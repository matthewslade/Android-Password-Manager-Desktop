package dpm

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import dpm.model.Password
import dpm.model.PasswordList
import java.util.prefs.*;
import java.io.*
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

object AuthenticationManager {

    var prefs = Preferences.userNodeForPackage(AuthenticationManager::class.java);

    var passwordList:PasswordList?=null

    var hash:String?=null;

    private var masterPassword:String?=null

    fun getMD5EncryptedString(encTarget: String): String {
        var mdEnc: MessageDigest? = null
        try {
            mdEnc = MessageDigest.getInstance("MD5")
        } catch (e: NoSuchAlgorithmException) {
            println("Exception while encrypting to md5")
        }
        // Encryption algorithm
        if(mdEnc==null)
            return ""

        mdEnc.update(encTarget.toByteArray(), 0, encTarget.length)
        var md5 = BigInteger(1, mdEnc.digest()).toString(34)
        while (md5.length < 24) {
            md5 = md5 + "0"
        }
        return md5
    }

    fun clearMasterPassword()
    {
        masterPassword = ""
    }

    fun setMasterPassword(password: String){
        masterPassword = password
        // write out getMD5EncryptedString(password).substring(2)
    }

    fun checkMasterPassword(password: String):Boolean
    {
        var md5Password = getMD5EncryptedString(password).substring(2)
        if(md5Password.equals(hash)) {
            masterPassword = password
            return true
        }
        return false
    }

    fun generatePassword(password: Password):String
    {
            return password.prefix.trim()+getMD5EncryptedString(password.label.toLowerCase().trim().replace(" ","")+password.version+masterPassword!!.trim()).substring(0,password.length)
    }

    fun loadPasswordList()
    {
        if(passwordList==null)
        {
            var file = File(Constants.Misc.LOCAL_FILENAME)
            var reader = JsonReader(FileReader(file));
            passwordList = Gson().fromJson<PasswordList>(reader, PasswordList::class.java);
            if(passwordList==null)
                passwordList = PasswordList(HashMap<String,Password>());

            hash = BufferedReader(FileReader(File(Constants.Misc.HASH_FILENAME))).readLine();
        }
    }

    fun PutPaswordListTask(callback:() -> Unit)
    {
        var passwordFileString = Gson().toJson(passwordList)
        var file = File(Constants.Misc.LOCAL_FILENAME)
        file.createNewFile()
        var fOut = FileOutputStream(file)
        var outWriter = OutputStreamWriter(fOut)
        outWriter.append(passwordFileString)
        outWriter.close()
        fOut.close()
        callback()
    }

    fun addorUpdatePassword(password: Password,callback:()-> Unit)
    {
        loadPasswordList()
        passwordList!!.passwords.put(password.label,password)
        PutPaswordListTask(callback)

    }

    fun deletePassword(password: Password,callback: () -> Unit)
    {
        loadPasswordList()
        if(passwordList!!.passwords.containsKey(password.label))
        {
            passwordList!!.passwords.remove(password.label)

            PutPaswordListTask(callback)
        }
    }
}