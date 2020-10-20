package com.cao.commons.Utils;

/**
 *  LICENSE AND TRADEMARK NOTICES
 *  
 *  Except where noted, sample source code written by Motorola Mobility Inc. and
 *  provided to you is licensed as described below.
 *  
 *  Copyright (c) 2012, Motorola, Inc.
 *  All  rights reserved except as otherwise explicitly indicated.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  - Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *
 *  - Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or recharge_unselect materials provided with the distribution.
 *
 *  - Neither the name of Motorola, Inc. nor the names of its contributors may
 *  be used to endorse or promote products derived from this software without
 *  specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER  CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 *  
 *  Other source code displayed may be licensed under Apache License, Version
 *  2.
 *  
 *  Copyright ¬© 2012, Android Open Source Project. All rights reserved unless
 *  otherwise explicitly indicated.
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy
 *  of the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0.
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations
 *  under the License.
 *  
 */

// Please refer to the accompanying article at 
// http://developer.motorola.com/docs/using_the_advanced_encryption_standard_in_android/
// A tutorial guide to using AES encryption in Android
// First we generate a 256 bit secret key; then we use that secret key to AES encrypt a plaintext message.
// Finally we decrypt the ciphertext to get our original message back.
// We don't keep a copy of the secret key - we generate the secret key whenever it is needed, 
// so we must remember all the parameters needed to generate it -
// the salt, the IV, the human-friendly passphrase, all the algorithms and parameters to those algorithms.
// Peter van der Linden, April 15 2012

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
	private final static String KEY_GENERATION_ALG = "PBKDF2WithHmacSHA1";
	private static final String ENCODING = "UTF-8";

	private final static int HASH_ITERATIONS = 10000;
	private final static int KEY_LENGTH = 128;

	private static String passphrase = "hqpprfvbnji13579-@$^*)qazpl,hqpp";

	private static byte[] salt = {106, 105, 106, 105, 97, 0x1, 0x5, 0x9, 0x9, 0x6, 
            0x3, 0x7, 0x3, 0x3, 0x9, 0x0}; // must save this for next time we want the key

	private static PBEKeySpec myKeyspec = new PBEKeySpec(passphrase.toCharArray(), salt,
			HASH_ITERATIONS, KEY_LENGTH);
	private final static String CIPHERMODEPADDING = "AES/CBC/PKCS7Padding";

	private static SecretKeyFactory keyfactory = null;
	private static SecretKey sk = null;
	private static SecretKeySpec skforAES = null;
	private static byte[] iv = {108, 105, 97, 110, 103, 0x1, 0x5, 0x9, 0x9, 0x6, 0x3, 0x7, 0x3, 0x3, 0x9, 0x0};

	private static IvParameterSpec IV;

	static {

		try {
			keyfactory = SecretKeyFactory.getInstance(KEY_GENERATION_ALG);
			sk = keyfactory.generateSecret(myKeyspec);

		} catch (NoSuchAlgorithmException nsae) {
			Log.e("djpt",
					"no key factory support for PBEWITHSHAANDTWOFISH-CBC");
		} catch (InvalidKeySpecException ikse) {
			Log.e("djpt", "invalid key spec for PBEWITHSHAANDTWOFISH-CBC");
		}

		byte[] skAsByteArray = sk.getEncoded();
		
		skforAES = new SecretKeySpec(skAsByteArray, "AES");

		IV = new IvParameterSpec(iv);

	}

	public static String encrypt(String plaintext) {

		byte[] ciphertext = encrypt(CIPHERMODEPADDING, skforAES, IV, plaintext);
		String base64_ciphertext = Base64Encoder.encode(ciphertext);
		return base64_ciphertext;
	}

	public static String decrypt(String ciphertext_base64) {
		byte[] s = Base64Decoder.decodeToBytes(ciphertext_base64);
		String decrypted = new String(decrypt(CIPHERMODEPADDING, skforAES, IV, s));
		return decrypted;
	}

	private static byte[] encrypt(String cmp, SecretKey sk, IvParameterSpec IV,
			String msg) {
		try {
			Cipher c = Cipher.getInstance(cmp);
			c.init(Cipher.ENCRYPT_MODE, sk, IV);
			return c.doFinal(msg.getBytes(ENCODING));
		} catch (NoSuchAlgorithmException nsae) {
			Log.e("djpt", "no cipher getinstance support for " + cmp);
		} catch (NoSuchPaddingException nspe) {
			Log.e("djpt", "no cipher getinstance support for padding " + cmp);
		} catch (InvalidKeyException e) {
			Log.e("djpt", "invalid key exception");
		} catch (InvalidAlgorithmParameterException e) {
			Log.e("djpt", "invalid algorithm parameter exception");
		} catch (IllegalBlockSizeException e) {
			Log.e("djpt", "illegal block size exception");
		} catch (BadPaddingException e) {
			Log.e("djpt", "bad padding exception");
		} catch (UnsupportedEncodingException e) {
		    Log.e("djpt", "unsupported Encoding Exception");
        }
		return null;
	}

	private static byte[] decrypt(String cmp, SecretKey sk, IvParameterSpec IV,
			byte[] ciphertext) {
		try {
			Cipher c = Cipher.getInstance(cmp);
			c.init(Cipher.DECRYPT_MODE, sk, IV);
			return c.doFinal(ciphertext);
		} catch (NoSuchAlgorithmException nsae) {
			Log.e("djpt", "no cipher getinstance support for " + cmp);
		} catch (NoSuchPaddingException nspe) {
			Log.e("djpt", "no cipher getinstance support for padding " + cmp);
		} catch (InvalidKeyException e) {
			Log.e("djpt", "invalid key exception");
		} catch (InvalidAlgorithmParameterException e) {
			Log.e("djpt", "invalid algorithm parameter exception");
		} catch (IllegalBlockSizeException e) {
			Log.e("djpt", "illegal block size exception");
		} catch (BadPaddingException e) {
			Log.e("djpt", "bad padding exception");
			e.printStackTrace();
		}
		return null;
	}

}