export class FileUtils {
  static binaryToString(bytes: Uint8Array): string {
    let binary: string = '';
    for (let i = 0; i < bytes.byteLength; i++) {
      binary += String.fromCharCode(bytes[i]);
    }
    return btoa(binary)
  }
}
