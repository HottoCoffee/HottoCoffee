import { getMimeTypeFromStream } from '../utils/mimeType';

export const post = async (request: Request, env: Env): Promise<Response> => {
	const formData = await request.clone().formData();
	const file = formData.get('image') as unknown as File;
	const isFile = file && file instanceof File;

	if (!isFile) {
		return new Response('No image file found in form-data', {
			status: 400,
			headers: { 'Content-Type': 'text/plain' },
		});
	}

	// ファイルをReadableStreamとして取得
	const readableStream = file.stream();

	const type = await getMimeTypeFromStream(readableStream);

	const key = `profile-image-${crypto.randomUUID()}`;

	const imageFile = (await request.clone().formData()).get('image') as unknown as File;

	await env.MY_BUCKET.put(key, imageFile.stream(), {
		httpMetadata: {
			contentType: type ?? 'application/octet-stream',
		},
	});
	return Response.json({ key });
};
