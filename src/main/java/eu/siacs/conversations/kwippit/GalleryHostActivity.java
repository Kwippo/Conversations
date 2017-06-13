//
// This file is Copyright (C) 2017 by Kwippit, Inc.
// All rights reserved.
//
// An explicit written license is required to use this file for any purpose.
//
package eu.siacs.conversations.kwippit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kwippit.sdk.gallery.GalleryFragment;
import com.kwippit.sdk.gallery.KwippitListDelegate;
import com.kwippit.sdk.rest.KwippitImage;

import eu.siacs.conversations.R;

public class GalleryHostActivity extends AppCompatActivity implements KwippitListDelegate
{
    private final static int COMPOSER_REQUEST_ID = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_host);

        GalleryFragment galleryFragment = (GalleryFragment) getSupportFragmentManager().findFragmentById(R.id.gallery_fragment);
        galleryFragment.setKwippitListDelegate(this);
    }

    @Override
    public void onKwippitClicked(KwippitImage kwippitImage)
    {
        Intent composerIntent = new Intent(this, ComposerHostActivity.class);
        composerIntent.putExtra("kwippit_image", kwippitImage);
        startActivityForResult(composerIntent, COMPOSER_REQUEST_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK  &&  requestCode == COMPOSER_REQUEST_ID)
        {
            setResult(resultCode, data);
            finish();
        }
    }
}
