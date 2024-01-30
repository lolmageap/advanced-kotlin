package coroutine

import kotlinx.coroutines.delay

suspend fun main() {
    val userService = UserService()
    val user = userService.findUser("1", null)
    printWithThreadName(user)
}

interface Continuation {
    suspend fun resumeWith(result: Any?)
}

class UserService {

    private val userProfileRepository = UserProfileRepository()
    private val userImageRepository = UserImageRepository()

    private abstract class FindUserContinuation : Continuation {
        var label: Int = 0
        var profile: Profile? = null
        var image: Image? = null
    }

    suspend fun findUser(userId: String, continuation: Continuation?): User {
        val sm: FindUserContinuation = continuation as? FindUserContinuation ?: object : FindUserContinuation() {

            override suspend fun resumeWith(result: Any?) {
                when (label) {
                    0 -> {
                        profile = result as Profile
                        label = 1
                    }

                    1 -> {
                        image = result as Image
                        label = 2
                    }
                }
                findUser(userId, this)
            }

        }
        when (sm.label) {
            0 -> {
                printWithThreadName("profile")
                userProfileRepository.findProfile(userId, sm)
            }

            1 -> {
                printWithThreadName("image")
                userImageRepository.findImage(sm.profile!!, sm)
            }
        }

        printWithThreadName("user")
        return User(userId, sm.image!!)

    }
}

data class User(val id: String, val image: Image)

class UserProfileRepository {
    suspend fun findProfile(userId: String, continuation: Continuation) {
        delay(1000)
        val profile = Profile("name", 10)
        continuation.resumeWith(profile)
    }
}

data class Profile(val name: String, val age: Int)

class UserImageRepository {
    suspend fun findImage(profile: Profile, continuation: Continuation) {
        delay(1000)
        val image = Image("https://www.naver.com", 100)
        return continuation.resumeWith(image)
    }
}

data class Image(val url: String, val size: Int)