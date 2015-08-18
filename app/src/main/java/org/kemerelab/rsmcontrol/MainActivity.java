package org.kemerelab.rsmcontrol;

import org.ndeftools.Message;
import org.ndeftools.MimeRecord;
import org.ndeftools.Record;
import org.ndeftools.externaltype.ExternalTypeRecord;
import org.ndeftools.util.activity.NfcReaderActivity;
import org.ndeftools.wellknown.TextRecord;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends NfcReaderActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void toast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

    public void updateStatus(String message) {
        TextView t = (TextView) findViewById(R.id.statusTextView);
        if ( t!= null)
            t.setText(message);
    }

    /**
     *
     * This device does not have NFC hardware
     *
     */

    @Override
    protected void onNfcFeatureNotFound() {
        toast(getString(R.string.noNfcMessage));
        //updateStatus(getString(R.string.noNfcMessage));
    }

    /**
     * NFC feature was found and is currently enabled
     */

    @Override
    protected void onNfcStateEnabled() {
        toast(getString(R.string.nfcAvailableEnabled));
        //updateStatus(getString(R.string.nfcAvailableEnabled));
    }

    /**
     * NFC feature was found but is currently disabled
     */

    @Override
    protected void onNfcStateDisabled() {
        toast(getString(R.string.nfcAvailableDisabled));
        //updateStatus(getString(R.string.nfcAvailableDisabled));
    }

    /**
     * NFC setting changed since last check. For example, the user enabled NFC in the wireless settings.
     */

    @Override
    protected void onNfcStateChange(boolean enabled) {
        if (enabled) {
            toast(getString(R.string.nfcAvailableEnabled));
            //updateStatus(getString(R.string.nfcAvailableEnabled));
        } else {
            toast(getString(R.string.nfcAvailableDisabled));
            //updateStatus(getString(R.string.nfcAvailableDisabled));
        }
    }

    @Override
    protected void onTagLost() {
        toast(getString(R.string.tagLost));
    }

    /**
     * An NDEF message was read and parsed. This method prints its contents to log and then shows its contents in the GUI.
     *
     * @param message the message
     */

    @Override
    public void readNdefMessage(Message message) {
        if (message.size() > 1) {
            toast(getString(R.string.readMultipleRecordNDEFMessage));
        } else {
            toast(getString(R.string.readSingleRecordNDEFMessage));
        }

    }

    /**
     * An empty NDEF message was read.
     *
     */

    @Override
    protected void readEmptyNdefMessage() {
        toast(getString(R.string.readEmptyMessage));
    }

    /**
     *
     * Something was read via NFC, but it was not an NDEF message.
     *
     * Handling this situation is out of scope of this project.
     *
     */

    @Override
    protected void readNonNdefMessage() {
        toast(getString(R.string.readNonNDEFMessage));
    }

}
