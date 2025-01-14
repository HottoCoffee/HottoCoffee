/**
 * Welcome to Cloudflare Workers! This is your first worker.
 *
 * - Run `npm run dev` in your terminal to start a development server
 * - Open a browser tab at http://localhost:8787/ to see your worker in action
 * - Run `npm run deploy` to publish your worker
 *
 * Bind resources to your worker in `wrangler.toml`. After adding bindings, a type definition for the
 * `Env` object can be regenerated with `npm run cf-typegen`.
 *
 * Learn more at https://developers.cloudflare.com/workers/
 */

import { S3Client, GetObjectCommand } from '@aws-sdk/client-s3';
import { getSignedUrl } from '@aws-sdk/s3-request-presigner';
import { post } from './controllers/post';

const BUCKET_NAME = 'profile-images';
const ACCOUNT_ID = 'd509ce1f3fa7033c5d4169443e122ae1';
const ACCESS_KEY_ID = '9eebf26d4d4db04a781f7ef984355047';
const SECRET_ACCESS_KEY = '7a457faa0367c4a0f713753dc52f082e8b9b595fb31ac5d7efe3210d5bc5d376';

const S3 = new S3Client({
	region: 'auto',
	endpoint: `https://${ACCOUNT_ID}.r2.cloudflarestorage.com`,
	credentials: {
		accessKeyId: ACCESS_KEY_ID,
		secretAccessKey: SECRET_ACCESS_KEY,
	},
});

export default {
	async fetch(request: Request, env: Env): Promise<Response> {
		const url = new URL(request.url);

		switch (request.method) {
			case 'POST': {
				return post(request, env);
			}
			case 'GET': {
				const key = url.pathname.slice(1);
				console.log({ key });
				const signedUrl = await getSignedUrl(S3, new GetObjectCommand({ Bucket: BUCKET_NAME, Key: key }), { expiresIn: 3600 });
				return new Response(signedUrl, {
					status: 200,
				});
			}
			case 'DELETE': {
				const key = url.pathname.slice(1);
				await env.MY_BUCKET.delete(key);
				return new Response('Deleted!');
			}

			default:
				return new Response('Method Not Allowed', {
					status: 405,
					headers: {
						Allow: 'PUT, GET, DELETE',
					},
				});
		}
	},
};
