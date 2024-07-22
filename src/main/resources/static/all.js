axios.interceptors.response.use(
    response => {
        console.log('interceptors 成功');
        console.log('statusCode:' + response.data.statusCode);

        if (response.data.statusCode !== 0) {
            return response.data.data;
        } else {
            return Promise.reject(response.data);
        }
    },
    error => {
        console.log('interceptors 失敗');
    }
)

axios.get("http://127.0.0.1:8080/BackendBase/test/user", {
    headers: {
        'Content-Type': 'application/json',

        'Access-Control-Allow-Origin': '*'
    }
})
    .then(function (response) {
        //成功
        console.log('成功');
        console.log(response);
    })
    .catch(function (error) {
        //錯誤
        console.log('錯誤');
        console.log(error.message);
    })