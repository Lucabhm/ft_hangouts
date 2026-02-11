package com.example.ft_hangouts.data.local

import android.provider.BaseColumns

object ContactContract {
    object ContactEntry : BaseColumns {
        const val TABLE_NAME = "contact"
        const val COLUMN_FIRST_NAME = "first_name"
        const val COLUMN_LAST_NAME = "last_name"
        const val COLUMN_PHONE_NUMBER = "phone_number"
        const val COLUMN_PROFILE_PIC = "profile_pic"
        const val COLUMN_LAST_MSG = "last_msg"
        const val COLUMN_CREATED_AT = "created_at"
    }

    object CallEntry : BaseColumns {
        const val TABLE_NAME = "call"
        const val COLUMN_CONTACT_ID = "contact_id"
        const val COLUMN_CREATED_AT = "created_at"
    }

    object MessageEntry : BaseColumns {
        const val TABLE_NAME = "message"
        const val COLUMN_MESSAGE = "message"
        const val COLUMN_FROM_ID = "from_id"
        const val COLUMN_SEND_TO_ID = "send_to_id"
        const val COLUMN_CREATED_AT = "created_at"
    }

}