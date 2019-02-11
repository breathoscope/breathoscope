package org.inspire.breath;
/*
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
*/
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity {

    ImageView mLogo;

    Button mLoginButton;
    Button mResetButton;

    EditText mUname;
    EditText mPass;

    private void findViews() {
        this.mLogo = findViewById(R.id.login_logo);
        this.mLoginButton = findViewById(R.id.login_login_btn);
        this.mResetButton = findViewById(R.id.login_reset_btn);
        this.mUname = findViewById(R.id.login_uname);
        this.mPass = findViewById(R.id.login_pass);
    }

    /*private Cipher getSymmetricKey(String input) {
        try {
            final byte[] salt = new byte[64];
            SecureRandom random = new SecureRandom();
            random.nextBytes(salt);

            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            KeySpec passwordBasedEncryptionKeySpec = new PBEKeySpec(input.toCharArray(), salt, 10000, 256);
            SecretKey secretKeyFromPBKDF2 = secretKeyFactory.generateSecret(passwordBasedEncryptionKeySpec);
            SecretKey key = new SecretKeySpec(secretKeyFromPBKDF2.getEncoded(), "AES");

            final byte[] nonce = new byte[32];
            random.nextBytes(nonce);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(16 * 8, nonce);
            cipher.init(Cipher.ENCRYPT_MODE, key, spec);

            return cipher;

        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();


        this.mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Cipher cipher = LoginActivity.this.getSymmetricKey(mPass.getText().toString());
//                if (cipher != null) {
//                    byte[] bytes = "password".getBytes();
//                    byte[] output = new byte[0];
//                    try {
//                        output = cipher.doFinal(bytes);
//                        String res = new String(output);
//                        System.out.println()
//                    } catch (BadPaddingException e) {
//                        e.printStackTrace();
//                    } catch (IllegalBlockSizeException e) {
//                        e.printStackTrace();
//                    }
//                }
            }
        });



    }
}
