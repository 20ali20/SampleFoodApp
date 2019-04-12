package alimojarrad.chownowtest.models

import alimojarrad.chownowtest.BaseModel

data class Restaurant (
    var name : String?=null,
    var locations : ArrayList<Location>?=null,
    var phone : String?=null,
    var address : Address?=null
):BaseModel()