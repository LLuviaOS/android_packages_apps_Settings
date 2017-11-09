/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.android.settings.display;

import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;

import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settings.overlay.FeatureFactory;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import androidx.preference.Preference;

import java.util.List;

import static com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_AMBIENT_DISPLAY;

public class AmbientDisplayLluviaPreferenceController extends AbstractPreferenceController implements PreferenceControllerMixin {

    static final String KEY_AMBIENT_LLUVIA = "ambient_display_lluvia";

    private final MetricsFeatureProvider mMetricsFeatureProvider;

    public AmbientDisplayLluviaPreferenceController(Context context) {
        super(context);
        mMetricsFeatureProvider = FeatureFactory.getFactory(context).getMetricsFeatureProvider();
    }

    @Override
    public boolean isAvailable() {
        return !mContext.getResources().getString(R.string.config_lluviaDozePackage).equals("");
    }

    @Override
    public String getPreferenceKey() {
        return KEY_AMBIENT_LLUVIA;
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (KEY_AMBIENT_LLUVIA.equals(preference.getKey())) {
            mMetricsFeatureProvider.action(mContext, ACTION_AMBIENT_DISPLAY);
            try {
                String[] lluviaDozePackage = mContext.getResources().getString(R.string.config_lluviaDozePackage).split("/");
                String activityName = lluviaDozePackage[0];
                String className = lluviaDozePackage[1];
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(activityName, className));
                mContext.startActivity(intent);
            } catch (Exception e){
            }
        }
        return false;
    }

    @Override
    public void updateNonIndexableKeys(List<String> keys) {
        keys.add(getPreferenceKey());
    }

}
