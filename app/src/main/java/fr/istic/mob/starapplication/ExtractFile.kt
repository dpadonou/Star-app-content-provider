package fr.istic.mob.starapplication

import android.content.ContentValues.TAG
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class ExtractFile {

 fun extract(location:String,zipFile:String){
        try {
            /** Créez le dossier de location s'il n'existe pas **/
            val f = File(location)
            if (!f.exists()) {
                f.mkdirs()
            }
            /** Dezipper le dossier et réeecrire chaque fichier dans le dossier de location **/
            val zin = ZipInputStream(FileInputStream(zipFile))
            zin.use { zin ->
                var ze: ZipEntry? = null
                while (zin.nextEntry.also {ze = it} != null) {
                    val path: String = location + ze!!.name
                    if (ze!!.isDirectory) {
                        val unzipFile = File(path)
                        if (!unzipFile.isDirectory){
                            unzipFile.mkdirs()
                        }
                    } else {
                        val fOut = FileOutputStream(path, false)
                        fOut.use { fOut ->
                            var c: Int = zin.read()
                            while (c != -1) {
                                fOut.write(c)
                                c = zin.read()
                            }
                            zin.closeEntry()
                        }
                    }
                }
                /**Supprimez après dezippage */
                val file = File(zipFile)
                file.delete()
                Log.i("","Succès du dezippage")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Unzip exception", e)
        }
    }
}