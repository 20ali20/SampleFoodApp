package alimojarrad.chownowtest.models

import alimojarrad.chownowtest.BaseModel

data class Address (
    var formatted_address : String?=null,
    var street_address1 : String?=null,
    var street_address2: String?=null,
    var city : String?=null,
    var state: String?=null,
    var zip : String?=null,
    var country : String?=null,
    var latitude : String?=null,
    var longitude : String?=null
):BaseModel()