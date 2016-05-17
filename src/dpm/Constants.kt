package dpm

class Constants{
    object SharedPrefs {
        val ACCESS_TOKEN = "apm.accessToken"
        val MASTER_PASSWORD_HASH = "apm.masterPasswordHash"
        val LAST_ACTIVITY = "apm.lastActivity"

    }

    object IntentKey{
        val PASSWORD = "password"
    }

    object Credentials{
        val APP_KEY = "w72uu1b53wlewpr"
        val APP_SECRET = "a0ccibz0ijb9q0p"
    }

    object Misc {
        val DEFAULT_LENGTH = 10
        val DEFAULT_PREFIX = "P_"
        val LOCAL_FILENAME = "/home/matthewslade/Dropbox/Apps/Android Password Manager/passwords.json"
        val HASH_FILENAME = "/home/matthewslade/hash.txt"
    }
}

