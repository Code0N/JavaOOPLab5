import java.io.*

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        var str = "hellNOOOOOOOO!!!"
        str = repeat(str, 10000000)
        val fileToWrite = File("writed")
        val fileToRead = File("readed")
        var startTimestamp: Long
        var endTimestamp: Long
        try {
            fileToWrite.createNewFile()
            startTimestamp = System.currentTimeMillis()
            val outputStream = FileOutputStream(fileToWrite)
            outputStream.write(str.toByteArray())
            outputStream.close()
            endTimestamp = System.currentTimeMillis()
            println(String.format("Writing without filters: %d ms", endTimestamp - startTimestamp))
            fileToWrite.delete()
            fileToWrite.createNewFile()
            startTimestamp = System.currentTimeMillis()
            val bufferedOutputStream = BufferedOutputStream(FileOutputStream(fileToWrite))
            bufferedOutputStream.write(str.toByteArray())
            bufferedOutputStream.close()
            val sb = StringBuffer()
            endTimestamp = System.currentTimeMillis()
            println(String.format("Writing with filters: %d ms", endTimestamp - startTimestamp))

            val routputStream = FileOutputStream(fileToRead)
            routputStream.write(str.toByteArray())
            routputStream.close()


            val inputStream = FileInputStream(fileToRead)
            var data = inputStream.readAllBytes()
            inputStream.close()

            endTimestamp = System.currentTimeMillis()
            println(String.format("Reading without filters: %d ms", endTimestamp - startTimestamp))

            val bufferedInputStream = BufferedInputStream(FileInputStream(fileToRead))
            data = bufferedInputStream.readAllBytes()
            bufferedInputStream.close()
            endTimestamp = System.currentTimeMillis()

            println(String.format("Reading with filters: %d ms", endTimestamp - startTimestamp))
            fileToRead.delete()
        } catch (e: IOException) {
            println("Problem has happened")
            println(e.message)
        } finally {
            fileToWrite.delete()
        }
    }

    fun repeat(str: String, times: Int): String {
        val sb = StringBuilder(str.length * times)
        for (i in 0 until times)
            sb.append(str)
        return sb.toString()
    }
}