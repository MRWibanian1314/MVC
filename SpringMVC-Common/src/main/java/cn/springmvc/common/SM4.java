package cn.springmvc.common;

import java.util.Arrays;

/**
 * 本算法是一个分组算法。该算法的分组长度为128比特，密钥长度为128比特，也就是16个字节。
 * 加密算法与密钥扩展算法都采用32轮非线性迭代结构。
 * 解密算法与加密算法的结构相同，只是轮密钥的使用顺序相反，解密轮密钥是加密轮密钥的逆序。
 * 所有在SMS4的基础类中，你会看到加密和解密的基础函数是同一个，
 * 只是需要一个int型的标志位来判断是加密还是解密
 * @author hetq
 */
public class SM4 {

    private static final int ENCRYPT = 1;
    private static final int DECRYPT = 0;
    public static final int ROUND = 32;
    private static final int BLOCK = 16;
    private static byte[] sbox = {(byte) 0xd6, (byte) 0x90, (byte) 0xe9, (byte) 0xfe,
        (byte) 0xcc, (byte) 0xe1, 0x3d, (byte) 0xb7, 0x16, (byte) 0xb6,
        0x14, (byte) 0xc2, 0x28, (byte) 0xfb, 0x2c, 0x05, 0x2b, 0x67,
        (byte) 0x9a, 0x76, 0x2a, (byte) 0xbe, 0x04, (byte) 0xc3,
        (byte) 0xaa, 0x44, 0x13, 0x26, 0x49, (byte) 0x86, 0x06,
        (byte) 0x99, (byte) 0x9c, 0x42, 0x50, (byte) 0xf4, (byte) 0x91,
        (byte) 0xef, (byte) 0x98, 0x7a, 0x33, 0x54, 0x0b, 0x43,
        (byte) 0xed, (byte) 0xcf, (byte) 0xac, 0x62, (byte) 0xe4,
        (byte) 0xb3, 0x1c, (byte) 0xa9, (byte) 0xc9, 0x08, (byte) 0xe8,
        (byte) 0x95, (byte) 0x80, (byte) 0xdf, (byte) 0x94, (byte) 0xfa,
        0x75, (byte) 0x8f, 0x3f, (byte) 0xa6, 0x47, 0x07, (byte) 0xa7,
        (byte) 0xfc, (byte) 0xf3, 0x73, 0x17, (byte) 0xba, (byte) 0x83,
        0x59, 0x3c, 0x19, (byte) 0xe6, (byte) 0x85, 0x4f, (byte) 0xa8,
        0x68, 0x6b, (byte) 0x81, (byte) 0xb2, 0x71, 0x64, (byte) 0xda,
        (byte) 0x8b, (byte) 0xf8, (byte) 0xeb, 0x0f, 0x4b, 0x70, 0x56,
        (byte) 0x9d, 0x35, 0x1e, 0x24, 0x0e, 0x5e, 0x63, 0x58, (byte) 0xd1,
        (byte) 0xa2, 0x25, 0x22, 0x7c, 0x3b, 0x01, 0x21, 0x78, (byte) 0x87,
        (byte) 0xd4, 0x00, 0x46, 0x57, (byte) 0x9f, (byte) 0xd3, 0x27,
        0x52, 0x4c, 0x36, 0x02, (byte) 0xe7, (byte) 0xa0, (byte) 0xc4,
        (byte) 0xc8, (byte) 0x9e, (byte) 0xea, (byte) 0xbf, (byte) 0x8a,
        (byte) 0xd2, 0x40, (byte) 0xc7, 0x38, (byte) 0xb5, (byte) 0xa3,
        (byte) 0xf7, (byte) 0xf2, (byte) 0xce, (byte) 0xf9, 0x61, 0x15,
        (byte) 0xa1, (byte) 0xe0, (byte) 0xae, 0x5d, (byte) 0xa4,
        (byte) 0x9b, 0x34, 0x1a, 0x55, (byte) 0xad, (byte) 0x93, 0x32,
        0x30, (byte) 0xf5, (byte) 0x8c, (byte) 0xb1, (byte) 0xe3, 0x1d,
        (byte) 0xf6, (byte) 0xe2, 0x2e, (byte) 0x82, 0x66, (byte) 0xca,
        0x60, (byte) 0xc0, 0x29, 0x23, (byte) 0xab, 0x0d, 0x53, 0x4e, 0x6f,
        (byte) 0xd5, (byte) 0xdb, 0x37, 0x45, (byte) 0xde, (byte) 0xfd,
        (byte) 0x8e, 0x2f, 0x03, (byte) 0xff, 0x6a, 0x72, 0x6d, 0x6c, 0x5b,
        0x51, (byte) 0x8d, 0x1b, (byte) 0xaf, (byte) 0x92, (byte) 0xbb,
        (byte) 0xdd, (byte) 0xbc, 0x7f, 0x11, (byte) 0xd9, 0x5c, 0x41,
        0x1f, 0x10, 0x5a, (byte) 0xd8, 0x0a, (byte) 0xc1, 0x31,
        (byte) 0x88, (byte) 0xa5, (byte) 0xcd, 0x7b, (byte) 0xbd, 0x2d,
        0x74, (byte) 0xd0, 0x12, (byte) 0xb8, (byte) 0xe5, (byte) 0xb4,
        (byte) 0xb0, (byte) 0x89, 0x69, (byte) 0x97, 0x4a, 0x0c,
        (byte) 0x96, 0x77, 0x7e, 0x65, (byte) 0xb9, (byte) 0xf1, 0x09,
        (byte) 0xc5, 0x6e, (byte) 0xc6, (byte) 0x84, 0x18, (byte) 0xf0,
        0x7d, (byte) 0xec, 0x3a, (byte) 0xdc, 0x4d, 0x20, 0x79,
        (byte) 0xee, 0x5f, 0x3e, (byte) 0xd7, (byte) 0xcb, 0x39, 0x48};
    private static int fk[] = {0xa3b1bac6, 0x56aa3350, 0x677d9197, 0xb27022dc};
    private static int[] ck = {0x00070e15, 0x1c232a31, 0x383f464d, 0x545b6269,
        0x70777e85, 0x8c939aa1, 0xa8afb6bd, 0xc4cbd2d9, 0xe0e7eef5,
        0xfc030a11, 0x181f262d, 0x343b4249, 0x50575e65, 0x6c737a81,
        0x888f969d, 0xa4abb2b9, 0xc0c7ced5, 0xdce3eaf1, 0xf8ff060d,
        0x141b2229, 0x30373e45, 0x4c535a61, 0x686f767d, 0x848b9299,
        0xa0a7aeb5, 0xbcc3cad1, 0xd8dfe6ed, 0xf4fb0209, 0x10171e25,
        0x2c333a41, 0x484f565d, 0x646b7279};

    private static int rotateLeft(int x, int n) {
        return x << n | x >>> (32 - n);
    }

    private static int ltrans(int b) {
        return b ^ rotateLeft(b, 2) ^ rotateLeft(b, 10) ^ rotateLeft(b, 18) ^ rotateLeft(b, 24);
    }

    private static int keyLtrans(int b) {
        return b ^ rotateLeft(b, 13) ^ rotateLeft(b, 23);
    }

    private static int substitute(int x) {
        return (sbox[x >>> 24 & 0xFF] & 0xFF) << 24
                | (sbox[x >>> 16 & 0xFF] & 0xFF) << 16
                | (sbox[x >>> 8 & 0xFF] & 0xFF) << 8
                | (sbox[x & 0xFF] & 0xFF);
    }

    private static void  crypt(byte[] input, byte[] output, int[] rk) {
        int r, mid, x0, x1, x2, x3;
        int[] x = new int[4];
        int[] tmp = new int[4];
        for (int i = 0; i < 4; i++) {
            tmp[0] = input[0 + 4 * i] & 0xff;
            tmp[1] = input[1 + 4 * i] & 0xff;
            tmp[2] = input[2 + 4 * i] & 0xff;
            tmp[3] = input[3 + 4 * i] & 0xff;
            x[i] = tmp[0] << 24 | tmp[1] << 16 | tmp[2] << 8 | tmp[3];
        }
        for (r = 0; r < 32; r += 4) {
            mid = x[1] ^ x[2] ^ x[3] ^ rk[r + 0];
            mid = substitute(mid);
            x[0] = x[0] ^ ltrans(mid); // x4

            mid = x[2] ^ x[3] ^ x[0] ^ rk[r + 1];
            mid = substitute(mid);
            x[1] = x[1] ^ ltrans(mid); // x5

            mid = x[3] ^ x[0] ^ x[1] ^ rk[r + 2];
            mid = substitute(mid);
            x[2] = x[2] ^ ltrans(mid); // x6

            mid = x[0] ^ x[1] ^ x[2] ^ rk[r + 3];
            mid = substitute(mid);
            x[3] = x[3] ^ ltrans(mid); // x7
        }

        // Reverse
        for (int j = 0; j < 16; j += 4) {
            output[j] = (byte) (x[3 - j / 4] >>> 24 & 0xFF);
            output[j + 1] = (byte) (x[3 - j / 4] >>> 16 & 0xFF);
            output[j + 2] = (byte) (x[3 - j / 4] >>> 8 & 0xFF);
            output[j + 3] = (byte) (x[3 - j / 4] & 0xFF);
        }
    }

    private static void keyExt(byte[] key, int[] rk, int cryptFlag) {
        int r, mid;
        int[] x = new int[4];
        int[] tmp = new int[4];
        for (int i = 0; i < 4; i++) {
            tmp[0] = key[0 + 4 * i] & 0xFF;
            tmp[1] = key[1 + 4 * i] & 0xff;
            tmp[2] = key[2 + 4 * i] & 0xff;
            tmp[3] = key[3 + 4 * i] & 0xff;
            x[i] = tmp[0] << 24 | tmp[1] << 16 | tmp[2] << 8 | tmp[3];
        }
        x[0] ^= fk[0];
        x[1] ^= fk[1];
        x[2] ^= fk[2];
        x[3] ^= fk[3];
        for (r = 0; r < 32; r += 4) {
            mid = x[1] ^ x[2] ^ x[3] ^ ck[r + 0];
            mid = substitute(mid);
            rk[r + 0] = x[0] ^= keyLtrans(mid); // rk0=K4

            mid = x[2] ^ x[3] ^ x[0] ^ ck[r + 1];
            mid = substitute(mid);
            rk[r + 1] = x[1] ^= keyLtrans(mid); // rk1=K5

            mid = x[3] ^ x[0] ^ x[1] ^ ck[r + 2];
            mid = substitute(mid);
            rk[r + 2] = x[2] ^= keyLtrans(mid); // rk2=K6

            mid = x[0] ^ x[1] ^ x[2] ^ ck[r + 3];
            mid = substitute(mid);
            rk[r + 3] = x[3] ^= keyLtrans(mid); // rk3=K7
        }

        // 解密时轮密钥使用顺序：rk31,rk30,...,rk0
        if (cryptFlag == DECRYPT) {
            for (r = 0; r < 16; r++) {
                mid = rk[r];
                rk[r] = rk[31 - r];
                rk[31 - r] = mid;
            }
        }
    }

    private static int sms4(byte[] in, int inLen, byte[] key, byte[] out, int cryptFlag) {
        int point = 0;
        int[] rk = new int[ROUND];
        keyExt(key, rk, cryptFlag);
        byte[] input = new byte[16];
        byte[] output = new byte[16];

        while (inLen >= BLOCK) {
            input = Arrays.copyOfRange(in, point, point + 16);
            crypt(input, output, rk);
            System.arraycopy(output, 0, out, point, BLOCK);
            inLen -= BLOCK;
            point += BLOCK;
        }

        return 0;
    }
    /**
     * 只加密16位明文
     *
     * @param plaintext
     * @param key
     * @return
     */
    private static byte[] encode16(byte[] plaintext, byte[] key) {
        byte[] cipher = new byte[16];
        sms4(plaintext, 16, key, cipher, ENCRYPT);
        return cipher;
    }

    /**
     * 只解密16位密文
     *
     * @param plaintext
     * @param key
     * @return
     */
    private static byte[] decode16(byte[] ciphertext, byte[] key) {
        byte[] plain = new byte[16];
        sms4(ciphertext, 16, key, plain, DECRYPT);
        return plain;
    }
    //////////////////////////////////////////////////////////////////////
    public static byte[] encode(String plaintext, byte[] key) {
        if (plaintext == null || plaintext.isEmpty()) {
            return null;
        }
        for (int i = plaintext.getBytes().length % 16; i < 16; i++) {
            plaintext += ' ';
        }

        return encode(plaintext.getBytes(), key);
    }

    /**
     * 不限明文长度的SMS4加密
     *
     * @param plaintext
     * @param key
     * @return
     */
    public static byte[] encode(byte[] plaintext, byte[] key) {
        byte[] ciphertext = new byte[plaintext.length];

        int k = 0;
        int plainLen = plaintext.length;
        while (k + 16 <= plainLen) {
            byte[] cellPlain = new byte[16];
            for (int i = 0; i < 16; i++) {
                cellPlain[i] = plaintext[k + i];
            }
            byte[] cellCipher = encode16(cellPlain, key);
            for (int i = 0; i < cellCipher.length; i++) {
                ciphertext[k + i] = cellCipher[i];
            }

            k += 16;
        }

        return ciphertext;
    }

    /**
     * 不限明文长度的SMS4解密
     *
     * @param ciphertext
     * @param key
     * @return
     */
    public static byte[] decode(byte[] ciphertext, byte[] key) {
        byte[] plaintext = new byte[ciphertext.length];

        int k = 0;
        int cipherLen = ciphertext.length;
        while (k + 16 <= cipherLen) {
            byte[] cellCipher = new byte[16];
            for (int i = 0; i < 16; i++) {
                cellCipher[i] = ciphertext[k + i];
            }
            byte[] cellPlain = decode16(cellCipher, key);
            for (int i = 0; i < cellPlain.length; i++) {
                plaintext[k + i] = cellPlain[i];
            }

            k += 16;
        }

        return plaintext;
    }

    public static void main(String[] args) {
        byte[] key = {0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab,
            (byte) 0xcd, (byte) 0xef, (byte) 0xfe, (byte) 0xdc,
            (byte) 0xba, (byte) 0x98, 0x76, 0x54, 0x32, 0x10};

        String newString = "Coding,你好！";

        byte[] enOut = encode(newString, key);
        if (enOut == null) {
            return;
        }

        System.out.println("加密结果:"+new String(enOut));
//        printBit(enOut);

        byte[] deOut = decode(enOut, key);
        System.out.println("解密结果:"+new String(deOut));
        //printBit(deOut);
        System.out.println("解密结果:"+new String(deOut));
    }
}
