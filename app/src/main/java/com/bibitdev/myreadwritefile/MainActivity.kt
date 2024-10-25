package com.bibitdev.myreadwritefile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bibitdev.myreadwritefile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNew.setOnClickListener(this)
        binding.btnOpen.setOnClickListener(this)
        binding.btnSave.setOnClickListener(this)
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_new -> newFile()
            R.id.btn_open -> showList()
            R.id.btn_save -> saveFile()
        }
    }

    private fun newFile() {
        binding.edtTitle.setText("")
        binding.edtFile.setText("")
        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show()
    }

    private fun showList() {
        val items = fileList()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pilih file yg diinginkan")
        builder.setItems(items) { dialog, item ->
            loadData(items[item].toString())
        }
        val alert = builder.create()
        alert.show()
    }

    private fun loadData(title: String) {
        val fileModel = FileHelper.readFromFile(this, title)
        binding.edtTitle.setText(fileModel.filename)
        binding.edtFile.setText(fileModel.data)
        Toast.makeText(this, "Loading " + fileModel.filename + " data", Toast.LENGTH_SHORT).show()
    }

    private fun saveFile() {
        when {
            binding.edtTitle.text.toString().isEmpty() -> Toast.makeText(
                this,
                "Title harus diisi terlebih dahulu",
                Toast.LENGTH_SHORT
            ).show()

            binding.edtFile.text.toString().isEmpty() -> Toast.makeText(
                this,
                "Kontent harus diisi terlebih dahulu",
                Toast.LENGTH_SHORT
            ).show()

            else -> {
                val title = binding.edtTitle.text.toString()
                val text = binding.edtFile.text.toString()
                val fileModel = FileModel()
                fileModel.filename = title
                fileModel.data = text
                FileHelper.wrireToFile(fileModel, this)
                Toast.makeText(this, "Saving " + fileModel.filename + " file", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


}

