package id.dipikul.githubfav.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import id.dipikul.githubfav.R
import id.dipikul.githubfav.alarm.AlarmReceiver
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    lateinit var alarmReceiver: AlarmReceiver
    companion object{
        const val SHARED_PREFERENCE = "sharedpreference"
        const val BOOLEAN_KEY = "booleankey"
        internal val TAG = SettingActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        supportActionBar?.title = "Setting"

        alarmReceiver = AlarmReceiver()

        val sharedPreference = getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE)

        val getBoolean = sharedPreference.getBoolean(BOOLEAN_KEY, false)
        cb_reminder.isChecked = getBoolean

        cb_reminder.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                val editor = sharedPreference.edit()
                editor.apply {
                    putBoolean(BOOLEAN_KEY, true)
                }.apply()

                alarmReceiver.setRepeatAlarm(this, AlarmReceiver.EXTRA_TYPE, "09:00")
                Log.d(TAG, "Alarm on")
            }else{
                val editor = sharedPreference.edit()
                editor.apply {
                    putBoolean(BOOLEAN_KEY, false)
                }.apply()

                alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_ALARM_REPEATING)
                Log.d(TAG, "Alarm off")
            }
        }

    }
}