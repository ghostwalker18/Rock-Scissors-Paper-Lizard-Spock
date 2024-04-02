package com.ghostwalker18.RSPLS;

import android.app.Application;;
import android.content.SharedPreferences;
import android.content.res.Resources;
import java.util.Locale;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.preference.PreferenceManager;

public class RSPLSApp extends Application implements SharedPreferences
        .OnSharedPreferenceChangeListener{
   private SharedPreferences sharedPreferences;

   @Override
   public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
      switch (key){
         case "language":
            String localeCode = sharedPreferences.getString(key, "en");
            setLocale(localeCode);
            break;
         case "theme":
            String theme = sharedPreferences.getString(key, "");
            setTheme(theme);
            break;
      }
   }

   @Override
   public void onCreate() {
      super.onCreate();
      sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
      String localeCode = sharedPreferences.getString("language", "en");
      setLocale(localeCode);
      String theme = sharedPreferences.getString("theme", "");
      setTheme(theme);
      sharedPreferences.registerOnSharedPreferenceChangeListener(this);
   }

   private void setLocale(String localeCode){
      LocaleListCompat localeListCompat;
      if(localeCode.equals("system")){
         localeListCompat = LocaleListCompat.getEmptyLocaleList();
      }
      else
         localeListCompat = LocaleListCompat.create(new Locale(localeCode));
      AppCompatDelegate.setApplicationLocales(localeListCompat);
   }

   private void setTheme(String theme){
      switch (theme) {
         case "system":
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            break;
         case "night":
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            break;
         case "day":
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            break;
      }
   }
}