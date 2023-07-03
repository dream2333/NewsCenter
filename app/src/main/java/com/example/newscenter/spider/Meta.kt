package com.example.newscenter.spider

class Meta {
    val categorys = listOf(
        Pair("https://temp.163.com/special/00804KVA/cm_guonei.js", "中国"),
        Pair("https://temp.163.com/special/00804KVA/cm_guoji.js", "国际"),
        Pair("https://temp.163.com/special/00804KVA/cm_war.js", "军事"),
        Pair("https://temp.163.com/special/00804KVA/cm_money.js", "财经"),
        Pair("https://temp.163.com/special/00804KVA/cm_sports.js", "体育"),
        Pair("https://temp.163.com/special/00804KVA/cm_tech.js", "科技"),
        Pair("https://temp.163.com/special/00804KVA/cm_jiankang.js", "健康")
    )
    var political = """
        {
        "suv": "230318153953BCS7",
        "pvId": "1688128952185_aU3Izo7",
        "clientType": 1,
        "resourceParam": [
            {
                "requestId": "1688128953629_23031815395_wEw",
                "resourceId": "959499089584783360",
                "page": 1,
                "size": 20,
                "spm": "smwp.channel_204.block2_185_5ZEeMr_1_fd",
                "context": {
                    "pro": "0,1",
                    "feedType": "XTOPIC_SYNTHETICAL"
                },
                "resProductParam": {
                    "productId": 1523,
                    "productType": 13,
                    "adTags": "20000111"
                },
                "productParam": {
                    "productId": 438647,
                    "productType": 15,
                    "categoryId": 47,
                    "mediaId": 1
                },
                "expParam": {}
            }
        ]
    }"""
    var international = """
    {
        "suv": "230318153953BCS7",
        "pvId": "1688129187345_sizAiP0",
        "clientType": 1,
        "resourceParam": [
            {
                "requestId": "1688129188245_23031815395_dwM",
                "resourceId": "959499089584783360",
                "page": 1,
                "size": 20,
                "spm": "smwp.channel_204.block2_185_5ZEeMr_1_fd",
                "context": {
                    "pro": "0,1",
                    "feedType": "XTOPIC_SYNTHETICAL"
                },
                "resProductParam": {
                    "productId": 1524,
                    "productType": 13,
                    "adTags": "20000111"
                },
                "productParam": {
                    "productId": 1649,
                    "productType": 13,
                    "categoryId": 47,
                    "mediaId": 1
                },
                "expParam": {}
            }
        ]
    }
    """
    var financial = """{
        "suv": "230318153953BCS7",
        "pvId": "1688129232458_yqlXnxV",
        "clientType": 1,
        "resourceParam": [
            {
                "requestId": "1688129233332_23031815395_PRz",
                "resourceId": "984482641447419904",
                "page": 1,
                "size": 20,
                "spm": "smwp.channel_204.common-ad-1",
                "context": {
                    "feedType": "XTOPIC_SYNTHETICAL"
                },
                "resProductParam": {
                    "productId": 1525,
                    "productType": 13,
                    "adTags": "20000111"
                },
                "productParam": {
                    "productId": 1525,
                    "productType": -1,
                    "categoryId": 47,
                    "mediaId": 1
                },
                "expParam": {}
            }
        ]
    }"""

}
