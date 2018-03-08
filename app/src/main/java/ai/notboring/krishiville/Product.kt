package ai.notboring.krishiville

import java.util.*

/**
 * Created by Sayan on 08/03/18.
 */

class Product {
    private var name: String = ""
    private var place: String = ""
    private var time: String = ""
    private var produce: String = ""
    private var height: Int = 0
    private var heightUnit: String = "feet"
    private var numBranches: Int = 0
    private var branchWeight: Int = 0
    private var grainWeight: Int = 0
    private var weightUnit: String = "grams"
    private var description: String =""

    fun Product() {}    // required for firestore

}