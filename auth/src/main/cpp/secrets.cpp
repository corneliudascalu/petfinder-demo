#include "secrets.hpp"

#include <jni.h>

#include "sha256.hpp"
#include "sha256.cpp"

/* Copyright (c) 2020-present Klaxit SAS
*
* Permission is hereby granted, free of charge, to any person
* obtaining a copy of this software and associated documentation
* files (the "Software"), to deal in the Software without
* restriction, including without limitation the rights to use,
* copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the
* Software is furnished to do so, subject to the following
* conditions:
*
* The above copyright notice and this permission notice shall be
* included in all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
* EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
* OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
* NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
* HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
* WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
* FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
* OTHER DEALINGS IN THE SOFTWARE.
*/

char *customDecode(char *str) {
    /* Add your own logic here
    * To improve your key security you can encode it before to integrate it in the app.
    * And then decode it with your own logic in this function.
    */
    return str;
}

jstring getOriginalKey(
        char *obfuscatedSecret,
        int obfuscatedSecretSize,
        jstring obfuscatingJStr,
        JNIEnv *pEnv) {

    // Get the obfuscating string SHA256 as the obfuscator
    const char *obfuscatingStr = pEnv->GetStringUTFChars(obfuscatingJStr, NULL);
    char buffer[2 * SHA256::DIGEST_SIZE + 1];

    sha256(obfuscatingStr, buffer);
    const char *obfuscator = buffer;

    // Apply a XOR between the obfuscated key and the obfuscating string to get original string
    char out[obfuscatedSecretSize + 1];
    for (int i = 0; i < obfuscatedSecretSize; i++) {
        out[i] = obfuscatedSecret[i] ^ obfuscator[i % strlen(obfuscator)];
    }

    // Add string terminal delimiter
    out[obfuscatedSecretSize] = 0x0;

    // (Optional) To improve key security
    return pEnv->NewStringUTF(customDecode(out));
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_riverpath_petfinderdemo_auth_Secrets_getclient_1secret(
        JNIEnv *pEnv,
        jobject pThis,
        jstring packageName) {
    char obfuscatedSecret[] = { 0x4a, 0x0, 0x6b, 0x65, 0x78, 0x57, 0x26, 0x13, 0x58, 0x71, 0x52, 0x4c, 0x6e, 0x3, 0x6, 0x5, 0x51, 0x51, 0x8, 0x57, 0xb, 0x42, 0x75, 0x67, 0x52, 0xb, 0x53, 0x5c, 0x2, 0x11, 0x2f, 0x67, 0x0, 0x3d, 0x7d, 0x37, 0x30, 0x1, 0xb, 0x4a };
    return getOriginalKey(obfuscatedSecret, sizeof(obfuscatedSecret), packageName, pEnv);
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_riverpath_petfinderdemo_auth_Secrets_getclient_1id(
        JNIEnv* pEnv,
        jobject pThis,
        jstring packageName) {
     char obfuscatedSecret[] = { 0x8, 0x25, 0x44, 0x74, 0x0, 0x56, 0x26, 0x2e, 0x7c, 0x5, 0x8, 0x5a, 0x73, 0x59, 0x30, 0x2e, 0x26, 0xd, 0x4b, 0x7f, 0x63, 0x7c, 0x7b, 0x67, 0x7e, 0x3b, 0x30, 0x7e, 0x55, 0x25, 0x4, 0x6a, 0x0, 0x8, 0x3, 0x27, 0xc, 0x11, 0xe, 0x7c, 0x5a, 0x44, 0x2d, 0x54, 0xe, 0x5a, 0x56, 0x63, 0x6c, 0x27 };
     return getOriginalKey(obfuscatedSecret, sizeof(obfuscatedSecret), packageName, pEnv);
}
