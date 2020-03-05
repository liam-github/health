//注意：该js必须在引入axios之后引入

//添加请求拦截器
axios.interceptors.request.use(function (config) {
    //在发送请求前要做的事
    //console.log(config);
    config.headers.token = localStorage.getItem("token");
    return config;
},function (error) {

    //对请求错误做些什么
    return Promise.reject(error);
});

//添加响应拦截器
axios.interceptors.response.use(function (response) {
    //收到响应数据后，对响应数据做些什么
    //console.log(response);
    return response;
},function (error) {
    // 如果发生403错误，则在页面弹出消息提示
    console.log(error.response);
    if (error.response.status == 403) {
        this.vue.$message({
            message:"没有权限",
            type:"error"
        });
    }
    return Promise.reject(error);
});