package com.petscare.org.vista.activitys

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.petscare.org.R
import com.petscare.org.databinding.ActivityRegistroBinding
import com.petscare.org.modelo.ModeloItemsSelector
import com.petscare.org.utilidades.CropImage
import com.petscare.org.utilidades.FileUtil
import com.petscare.org.utilidades.KeyboardUtils
import com.petscare.org.viewmodel.ViewModelRegistro
import com.petscare.org.vista.Interfaces.OnFragmentNavigationListener
import com.petscare.org.vista.adaptadores.dialogos.AdaptadorListaOpciones
import com.petscare.org.vista.fragments.registro.*
import com.yalantis.ucrop.UCrop
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class ActivityRegistro : AppCompatActivity(), OnFragmentNavigationListener {

    private lateinit var binding: ActivityRegistroBinding
    private val vmRegistro: ViewModelRegistro by viewModels()

    private var index: Int = 0
    private lateinit var frag_nombre: FragmentNombre
    private lateinit var frag_edad_genero: FragmenteEdadGenero
    private lateinit var frag_pais_telefono: FragmentPaisTelefono
    private lateinit var frag_verificacion: FragmentVerificar
    private lateinit var frag_correo_Correo_contrasena: FragmentCorreoContrasena
    private lateinit var frag_terminar: FragmentTerminar

    private lateinit var archivo_foto: File
    private lateinit var ruta_foto: String
    private lateinit var ruta_foto_absoluta: String

    private val PERMISO_CAMARA = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.THEME_GLOBAL_APP)
        super.onCreate(savedInstanceState)

        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        crearFragments()
        mostrarFragment(index)
        observarTeclado()
        eventosUI()

    }

    private fun crearFragments() {
        this.index = vmRegistro.getIndex()!!
        frag_nombre = FragmentNombre()
        frag_edad_genero = FragmenteEdadGenero()
        frag_pais_telefono = FragmentPaisTelefono()
        frag_verificacion = FragmentVerificar()
        frag_correo_Correo_contrasena = FragmentCorreoContrasena()
        frag_terminar = FragmentTerminar()
    }

    override fun mostrarFragment(index: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.slide_in,
            R.anim.fade_out,
            R.anim.fade_in,
            R.anim.slide_out
        )
        this.index = index
        when (this.index) {
            0 -> transaction.replace(R.id.contenedor_frags_registro, frag_nombre).commit()
            1 -> transaction.replace(R.id.contenedor_frags_registro, frag_edad_genero).commit()
            2 -> transaction.replace(R.id.contenedor_frags_registro, frag_correo_Correo_contrasena)
                .commit()
            3 -> {
                transaction.replace(R.id.contenedor_frags_registro, frag_pais_telefono).commit()
                binding.txtInfo.text = "Información de la cuenta"
            }
            4 -> {
                transaction.replace(R.id.contenedor_frags_registro, frag_verificacion).commit()
                binding.txtInfo.text = "Verificación de la cuenta"
            }
            5 -> {
                transaction.replace(R.id.contenedor_frags_registro, frag_terminar).commit()
                binding.txtInfo.visibility = View.GONE
            }
        }
    }

    private fun observarTeclado() {
        KeyboardUtils.addKeyboardToggleListener(this,
            object : KeyboardUtils.SoftKeyboardToggleListener {
                override fun onToggleSoftKeyboard(isVisible: Boolean) {
                    if (isVisible) {
                        binding.appBar.setExpanded(false, true)
                    } else {
                        binding.appBar.setExpanded(true, true)
                    }
                }
            })
    }

    private fun eventosUI() {
        binding.imgFoto.setOnClickListener { mostrarSelectorFoto() }
    }

    private fun mostrarSelectorFoto() {
        val items = ArrayList<ModeloItemsSelector>()
        items.add(ModeloItemsSelector("Camara", R.drawable.ic_camera))
        items.add(ModeloItemsSelector("Galeria", R.drawable.ic_galeria))
        items.add(ModeloItemsSelector("Cancelar", R.drawable.ic_cancelar))

        val dialogo = MaterialAlertDialogBuilder(this)
            .setTitle("Establecer foto de perfil")
            .setAdapter(
                AdaptadorListaOpciones.getAdaptador(
                    this,
                    items
                )
            ) { dialog_interface, index ->
                when (index) {
                    0 -> verificarPermisosCamara()
                    1 -> abrirGaleria()
                    2 -> dialog_interface.dismiss()
                }
            }.show()
    }

    private fun abrirCamara() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)
        if (intent.resolveActivity(packageManager) != null) {
            var archivo_foto: File? = null
            try {
                archivo_foto = crearArchivoFoto()
            } catch (e: Exception) {
                Toast.makeText(this, "Hubo un error al crear el archivo de la foto", Toast.LENGTH_SHORT).show()
            }

            if (archivo_foto != null) {
                val foto_uri: Uri = FileProvider.getUriForFile(this, "com.petscare.org", archivo_foto)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, foto_uri)
                resultado_camara.launch(intent)
            } else {
                Toast.makeText(this, "Hubo un error al cargar el archivo de imagen", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Hubo un error al abrir la camara", Toast.LENGTH_SHORT).show()
        }
    }

    private fun verificarPermisosCamara(){
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
           abrirCamara()
        } else {
            solicitarPermisosCamara()
        }
    }

    private fun solicitarPermisosCamara() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),PERMISO_CAMARA)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISO_CAMARA && grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            abrirCamara()
        } else{
            solicitarPermisosCamara()
        }
    }

    private fun crearArchivoFoto(): File {
        val directorio_almacenamiento = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        archivo_foto = File.createTempFile("${Date()}_foto", ".jpg", directorio_almacenamiento)
        ruta_foto = "file: ${archivo_foto.absolutePath}"
        ruta_foto_absoluta = archivo_foto.absolutePath
        return archivo_foto
    }

    private fun abrirGaleria() {
        val intent = Intent()
            .setAction(Intent.ACTION_PICK)
            .setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        resultado_galeria.launch(intent)
    }

    private val resultado_camara = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK){
            resultado_recorte.launch(Pair(Uri.fromFile(archivo_foto),Uri.fromFile(archivo_foto)))
        }
    }

    private val resultado_galeria = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val uri_origen = result.data!!.data
        var archivo_foto_galeria :File? = null
        try {
            archivo_foto_galeria = crearArchivoFoto()
        } catch (e : Exception){
            Toast.makeText(this, "Hubo un error para crear el archivo de la foto", Toast.LENGTH_SHORT).show();
        }

        val uri_destino = Uri.fromFile(archivo_foto_galeria)
        resultado_recorte.launch(Pair(uri_origen,uri_destino) as Pair<Uri, Uri>?)
    }

    private val resultado_recorte = registerForActivityResult(CropImage()) {
        val uri = it ?: return@registerForActivityResult // this is the output Uri
        binding.imgFoto.setImageURI(uri)
        mostrarFoto(uri)
        }

    private fun mostrarFoto(uri: Uri) {
        try {
            //Intentar crear un arvhivo con la ruta de la imagen
            archivo_foto = FileUtil.from(this, uri)

            //Crear un bitmap apartir de el archivo
            val bitmap = BitmapFactory.decodeFile(archivo_foto.absolutePath)

            //Crear un bitmap redondo apartir del bitmap anterior y establecer el radio del redondeo del circulo
            val round_bitmap = RoundedBitmapDrawableFactory.create(resources,bitmap)
            round_bitmap.cornerRadius = 300f

            //Quitar el icono (recurso src) de la imagen de foto de perfil
            binding.imgFoto.setImageResource(0)

            //Establecer de fondo la foto del usuario
            binding.imgFoto.background = round_bitmap

        } catch (e : IOException){
            Toast.makeText(this, "Hubo un error " + e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStop() {
        super.onStop()
        vmRegistro.setIndex(this.index)
    }

    override fun onBackPressed() {
        this.index--
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.slide_out, R.anim.slide_in, R.anim.fade_out)
        when (this.index) {
            -1 -> finish()
            0 -> transaction.replace(R.id.contenedor_frags_registro, frag_nombre).commit()
            1 -> transaction.replace(R.id.contenedor_frags_registro, frag_edad_genero).commit()
            2 -> transaction.replace(R.id.contenedor_frags_registro, frag_correo_Correo_contrasena)
                .commit()
            else -> index++
        }
    }
}