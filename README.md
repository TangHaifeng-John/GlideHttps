###	问题背景
最近在做项目中，发现图片加载不出来了，开始以为是布局出问题了，经过一顿操作后，无果，郁闷，之前都能加载，没办法，只好从URL地址下手了，图片是七牛云地址，从浏览器中打开是正常的，郁闷，我再次检查了一下代码，没有发现异常，正在苦思冥想中，发现图片地址是https，怀疑Glide是不是无法加载https的图片呢，于是我先把地址从json数据中取出来，然后，把地址修改成Http的，运行后，图片加载成功，原因已找到，Glide无法加载Https，那么现在就需要解决怎么放Glide加载Https呢？

通过查询资料后，Okhttp结合Glide可以解决这个问题，但是网上的资料都不齐全也没有完整Demo，所以只好自己一步一步来找了

####	第一步，解决Glide结合Okhttp问题
####	第二步，解决okhttp忽略https证书问题


[完整的Demo地址](https://github.com/TangHaifeng-John/GlideHttps)，Demo中使用的Glide4.x版本，请各位注意检查Glide版本，老版本跟4.x版本不太一致




贴一下重要的代码

####	绕开证书
```
 fun getUnsafeOkHttpClient(): OkHttpClient {


        try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) {
                }

                override fun checkServerTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) {
                }

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return arrayOf()
                }
            })
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            val sslSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier(HostnameVerifier { hostname, session -> true })

            builder.connectTimeout(20, TimeUnit.SECONDS)
            builder.readTimeout(20, TimeUnit.SECONDS)

            return builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
```

###	重写GlideModule


```
@GlideModule
class OkHttpGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        // Do nothing.
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val client = UnsafeOkHttpClient.getUnsafeOkHttpClient()
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(client))


    }


}
```
