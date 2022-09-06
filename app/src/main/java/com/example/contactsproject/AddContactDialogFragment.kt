package com.example.contactsproject

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.example.contactsproject.databinding.AddContactLayoutBinding

class AddContactDialogFragment : DialogFragment() {

    private lateinit var binding: AddContactLayoutBinding;
    private lateinit var activityContext: MainActivity
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var contactIconToReturn: Uri

    /**
     * Interface to save contact when user click on save button
     */
    interface OnContactSave {
        fun onContactSave(contactData: ContactData)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddContactLayoutBinding.inflate(inflater, container, false)

        /**
         * creates function to create a new activity that opens user gallery to chose icon
         * for new contact
         */
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    contactIconToReturn = data?.data!!
                    binding.contactChangeIcon.setImageURI(data.data!!)
                }
            }
        /**
         * Open gallery
         */
        binding.contactChangeIcon.setOnClickListener() {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intent)
        }

        binding.saveDataBtn.setOnClickListener() {
            dismiss()
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityContext = context as MainActivity
    }

    /**
     * call onContactSave function in main activity and send them new Contact with written information
     * from user
     */
    override fun onDismiss(dialog: DialogInterface) {
        activityContext.onContactSave(
            ContactData(
                contactIconToReturn.toString(),
                binding.usernameTextInput.text.toString(),
                binding.phoneNumberTextInput.text.toString()
            )
        )
        super.onDismiss(dialog)
    }


}