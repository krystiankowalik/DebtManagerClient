package kryx07.expensereconcilerclient.network

class AuthApiClient :
        BaseApiClient<AuthApiService>("http://192.168.43.154:8080/", AuthApiService::class.java)