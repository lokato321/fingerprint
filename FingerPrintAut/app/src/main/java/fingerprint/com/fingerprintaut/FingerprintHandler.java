package fingerprint.com.fingerprintaut;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 * Created by Usuario on 7/06/2017.
 */


//Debemos darle un extends y escribir FingerprintManager.AuthenticationCallBack
//Esto es para poder eliminar unos errores dentro del metodo startAuthentication
//Tambien nos sirve para cuando llamemos nuestros dos constructores onAuthenticationFailed y onAuthenticationSucceded
//Ojo si no agregamos el extends no podremos lograr agregar los metodos construct.
class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
    //Es necesario esta linea Context o nos dará error dentro del metodo FingerprintHandler
    private Context context;

    public FingerprintHandler(Context context) {
        this.context = context;
    }


    public void startAuthentication(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cenCancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED)
            return;
        fingerprintManager.authenticate(cryptoObject,cenCancellationSignal,0,this,null);
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        //Se define el mensaje de error que saldrá al no podes autenticar la huella digital.
        Toast.makeText(context,"Ha fallado",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        //Se define hacia que Activity queremos que nos redirija al autenticarse correctamente
        context.startActivity(new Intent(context,Main2Activity.class));
    }
}
