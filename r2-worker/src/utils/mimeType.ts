import { fileTypeFromStream } from 'file-type';

export const getMimeTypeFromStream = async (stream: ReadableStream) => {
	const fileType = await fileTypeFromStream(stream);
	if (fileType) {
		console.log(fileType.mime); // MIMEタイプを表示
		return fileType.mime;
	}
	return null;
};
