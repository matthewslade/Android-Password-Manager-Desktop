package dpm.model

import java.io.Serializable

data class Password ( var label:String,var version:Int, var length:Int,var prefix:String,var username:String):Serializable
