package com.xfl.kakaotalkbot

import org.mozilla.javascript.Function
import org.mozilla.javascript.ScriptableObject

 class ScriptsManager {
    private lateinit var responder: Function
    private lateinit var execScope: ScriptableObject
    private lateinit var onStartCompile: Function
    private var optimization: Int = 0
    private var scope: ScriptableObject? = null
    var onCreate: Function? = null
        private set
    var onStop: Function? = null
        private set
    var onResume: Function? = null
        private set
    var onPause: Function? = null
        private set

    @Deprecated("")
    constructor(responder: Function, execScope: ScriptableObject, onStartCompile: Function, scope: ScriptableObject) {
        this.responder = responder
        this.execScope = execScope
        this.onStartCompile = onStartCompile
        this.scope = scope
    }

    constructor() {}

    fun setResponder(responder: Function): ScriptsManager {
        this.responder = responder
        return this
    }

    fun setExecScope(execScope: ScriptableObject): ScriptsManager {
        this.execScope = execScope
        return this
    }

    fun setOnStartCompile(onStartCompile: Function): ScriptsManager {
        this.onStartCompile = onStartCompile
        return this
    }

    fun setOptimization(optimization: Int): ScriptsManager {
        this.optimization = optimization
        return this
    }

    fun getScope(): ScriptableObject? {
        return scope
    }

    fun setScope(scope: ScriptableObject): ScriptsManager {
        this.scope = scope
        return this
    }

    fun setScriptActivity(onCreate: Function, onStop: Function, onResume: Function, onPause: Function): ScriptsManager {
        this.onCreate = onCreate
        this.onStop = onStop
        this.onResume = onResume
        this.onPause = onPause
        return this
    }
fun getExecScope():ScriptableObject{
    return execScope
}
fun getOnStartCompile():Function{
    return onStartCompile
}
     fun getResponder():Function{
         return responder
     }
     fun getOptimization():Int{
         return optimization
     }
     companion object {
        var scriptName: String? = null

     }

}