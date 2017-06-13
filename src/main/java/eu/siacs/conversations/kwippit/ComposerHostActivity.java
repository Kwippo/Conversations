//
// This file is Copyright (C) 2017 by Kwippit, Inc.
// All rights reserved.
//
// An explicit written license is required to use this file for any purpose.
//
package eu.siacs.conversations.kwippit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.kwippit.sdk.composer.ComposerFragment;
import com.kwippit.sdk.rest.KwippitImage;
import com.kwippit.sdk.utils.KLog;

import java.io.File;

import eu.siacs.conversations.R;

public class ComposerHostActivity extends AppCompatActivity implements ComposerFragment.ComposerDelegate
{
    private static final String TAG = "ComposerHostActivity";
    private ComposerFragment composerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composer_host);

        KwippitImage kwippitImage = getIntent().getParcelableExtra("kwippit_image");

        composerFragment = (ComposerFragment)getSupportFragmentManager().findFragmentById(R.id.composer_fragment);
        composerFragment.setComposerDelegate(this);
        composerFragment.setKwippitImage(kwippitImage);

        ((Button)findViewById(R.id.send_button)).setOnClickListener(onSend);
    }

    private View.OnClickListener onSend = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            composerFragment.renderToFile();
        }
    };

    @Override
    public void onRenderComplete(File file, KwippitImage kwippitImage)
    {
        final Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
        Intent resultIntent = new Intent();
        resultIntent.putExtra(Intent.EXTRA_STREAM, uri);
        resultIntent.putExtra(ComposerFragment.EXTRA_KWIPPIT_IMAGE, kwippitImage);

        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onRenderFail(Exception ex)
    {
        KLog.e(TAG, "Exception caught while rendering Kwippit", ex);
    }
}
