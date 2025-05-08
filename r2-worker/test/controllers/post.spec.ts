import { describe, it, expect, vi } from 'vitest';
import { post } from '../../src/controllers/post';

describe('post controller', () => {
	it('should return 400 if no image file is found in form-data', async () => {
		const request = new Request('http://localhost', {
			method: 'POST',
			body: new FormData(),
		});

		const env = { MY_BUCKET: { put: vi.fn() } };

		const response = await post(request, env as any);
		expect(response.status).toBe(400);
		const text = await response.text();
		expect(text).toBe('No image file found in form-data');
	});

	it('should upload the image and return the key', async () => {
		const formData = new FormData();
		const file = new File(['dummy content'], 'dummy.png', { type: 'image/png' });
		formData.append('image', file);

		const request = new Request('http://localhost', {
			method: 'POST',
			body: formData,
		});

		const env = { MY_BUCKET: { put: vi.fn() } };

		global.crypto = {
			randomUUID: vi.fn().mockReturnValue('test-uuid'),
		} as any;

		const response = await post(request, env as any);
		expect(response.status).toBe(200);
		const json = await response.json();
		expect(json.key).toBe('profile-image-test-uuid');
	});
});
