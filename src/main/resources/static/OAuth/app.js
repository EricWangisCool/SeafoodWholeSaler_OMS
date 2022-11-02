const express = require('express')
const bodyparesr = require('body-parser')
const app = express()
const port = 3000

app.use(
    session({
        secret: 'googleAuth'
    })
)

//不放這個由axios發出的post 拿到的req.body會無法解析
app.use(bodyparesr.json())

// 這裡顯示登入頁的路由
app.get('/login', (req, res) => {
    return res.sendFile(`${__dirname}/index.html`)
})
app.post('/auth/google', async (req, res) => {

    //引入官方的套件
    const { OAuth2Client } = require('google-auth-library')
    const CLIENT_ID =
        '441870321038-1bdt5mnah9bf903dbfm9mcp7vqgiua3f.apps.googleusercontent.com'
    const client = new OAuth2Client(CLIENT_ID)
    const token = req.body.id_token

    //將token和client_Id放入參數一起去做驗證
    const ticket = await client.verifyIdToken({
        idToken: token,
        audience: CLIENT_ID
    })

    //拿到的ticket就是換回來的使用者資料
    console.log(ticket)

    //以下就個人需求看要拿資料做哪些使用
    //ex 使用者資訊存入資料庫，把資料存到 session內 等等
})

app.listen(3000, () => {
    console.log(`operate server at port: ${port}`)
})