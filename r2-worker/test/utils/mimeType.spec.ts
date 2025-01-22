import { describe, it, expect, vi } from 'vitest';
import { getMimeTypeFromStream } from '../../src/utils/mimeType';
import { Readable } from 'stream';
import { fileTypeFromStream } from 'file-type';

vi.mock('file-type', () => ({
	fileTypeFromStream: vi.fn(),
}));

describe('getMimeTypeFromStream', () => {
	it('should return the correct MIME type when fileType is found', async () => {
		const mockStream = new Readable();
		mockStream._read = () => {}; // No-op

		const mockMimeType = 'image/png';
		(fileTypeFromStream as any).mockResolvedValue({ mime: mockMimeType });

		const result = await getMimeTypeFromStream(mockStream as any);
		expect(result).toBe(mockMimeType);
	});

	it('should return null when fileType is not found', async () => {
		const mockStream = new Readable();
		mockStream._read = () => {}; // No-op

		(fileTypeFromStream as any).mockResolvedValue(null);

		const result = await getMimeTypeFromStream(mockStream as any);
		expect(result).toBeNull();
	});

	it("shouldn't throw an error when the stream is invalid", async () => {
		const invalidStream = null;

		await expect(await getMimeTypeFromStream(invalidStream as any)).toBe(null);
	});
});
