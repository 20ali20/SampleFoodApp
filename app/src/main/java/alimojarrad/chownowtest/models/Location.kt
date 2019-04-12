package alimojarrad.chownowtest.models

import alimojarrad.chownowtest.BaseModel

data class Location (
    var cuisines : ArrayList<String>?=null,
    var short_name : String?=null,
    var phone : String?=null,
    var address : Address?=null
):BaseModel()