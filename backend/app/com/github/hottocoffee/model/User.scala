package com.github.hottocoffee.model

case class User(id: Long,
                accountId: String,
                email: String,
                displayName: String,
                introduction: String,
                iconImageKey: String | Null) // TODO: UUID type
