package com.binar.challange_part5.profile

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.binar.challange_part5.R
import com.binar.challange_part5.User
import com.binar.challange_part5.ViewModelFactory
import com.binar.challange_part5.databinding.FragmentProfileBinding
import com.binar.challange_part5.home.HomeFragmentDirections
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class Profile : Fragment() {
    private val REQUEST_CODE_PERMISSION = 100
    var imagePath :String?=null
    //KAMERA
    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            Log.d("HASILKAMERA",result.toString())
            imagePath = result.toString()
            binding.imageprofile.setImageURI(result)
            Glide.with(requireActivity())
                .load(result)
                .apply(RequestOptions.centerCropTransform())
                .error(R.drawable.ic_launcher_background)
                .into(binding.imageprofile)
        }

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel : ProfileViewModel
    private var useernameValue = "default"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory(view.context)
        viewModel = ViewModelProvider(requireActivity(),factory)[ProfileViewModel::class.java]
        username()
        setData()

        binding.imageprofile.setOnClickListener {
            checkingPermissions()
        }


        binding.logoutprofile.setOnClickListener {
            viewModel.logOut()
            findNavController().navigate(ProfileDirections.actionProfileToLoginFragment())
        }
        binding.updateprofile.setOnClickListener {
            val user = User(
                userName = binding.profileusername.text.toString(),
                imagePath = imagePath
            )
            Log.d("GALERI",imagePath.toString())
            viewModel.getUsername().observe(viewLifecycleOwner){
                viewModel.updateData(user,it)
            }

            viewModel.getUpdateValidation().observe(viewLifecycleOwner){
                if(it==true){
                    viewModel.setUsername(user.userName)
                    viewModel.getUserData(user.userName)
                    Toast.makeText(requireContext(),"Profile Sudah Diganti",Toast.LENGTH_LONG).show()
                    findNavController().navigate(ProfileDirections.actionProfileToHomeFragment())
                }
            }
            setData()
        }
        binding.logoutprofile.setOnClickListener {
            logOut()
        }
    }
    private fun checkingPermissions() {
        if (isGranted(
                requireActivity(),
                Manifest.permission.CAMERA,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE_PERMISSION,
            )
        ) {
            chooseImageDialog()
        }
    }
    private fun isGranted(
        activity: Activity,
        permission: String,
        permissions: Array<String>,
        request: Int,
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }
    private fun chooseImageDialog() {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setMessage("Pilih Gambar")
            .setPositiveButton("Gallery") { _, _ -> openGallery() }
            .show()
    }
    private fun openGallery() {
        activity?.intent?.type = "image/*"
        galleryResult.launch("image/*")
    }




    private fun showPermissionDeniedDialog() {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton(
                "App Settings"
            ) { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", activity?.packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun username(){
        viewModel.getUsername().observe(viewLifecycleOwner){result ->
            useernameValue = result
            viewModel.getUserData(result)
        }
    }
    private fun setData(){
        //Set Data
        viewModel.resultUser().observe(viewLifecycleOwner){
            if(it.userName!==null){
                binding.profileusername.setText(it.userName.toString())
            }
            if(it.imagePath!=null){
                Log.d("GGG",it.imagePath.toString())
                imagePath = it.imagePath
                Glide.with(requireActivity())
                    .load(it.imagePath)
                    .apply(RequestOptions.centerCropTransform())
                    .error(R.drawable.ic_launcher_background)
                    .into(binding.imageprofile)
            }
        }
    }
    private fun logOut(){
        AlertDialog.Builder(context).setPositiveButton("Yes"){ _, _ ->
            viewModel.logOut()
            findNavController().navigate(ProfileDirections.actionProfileToLoginFragment())
        }
            .setNegativeButton(
                "No"
            ){
                    p0,_ ->
                p0.dismiss()
            }
            .setMessage("Apakah anda ingin Keluar?").setTitle("Confirm Logout")
            .create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}