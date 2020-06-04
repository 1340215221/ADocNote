package com.rh.note.vo

class RecentlyOpenedRecordVO {

    String projectName
    String projectPath

    String toString(){
        //return "${author}: ${content}"

        return """<html>
         <body>
           <p><b><i>${projectName}:</i></b></p>
           <p>${projectPath}</p>
         </body>
       </html>"""
    }

}
