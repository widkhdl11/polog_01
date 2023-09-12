import { json, redirect } from 'react-router-dom';
import PageContent from '../UI/PageContent'
import BackGroundImg from '../components/BackGroundImg'
import LoginForm from '../components/LoginForm';
import axios from 'axios';

const LoginPage = () => {
    return(
        <>
        <BackGroundImg>
            <PageContent title="Login">
                <LoginForm></LoginForm>
            </PageContent>
        </BackGroundImg>
        </>

    )
}

export default LoginPage;

export const loader = async() =>{

}

export const action = async({request, params}) =>{
    const data = await request.formData();

    const loginData = {
        id : data.get("id"),
        password : data.get("password"),
    }
    const response = await axios({
        url : "/api/login",
        method : 'POST',
        data : loginData
    }).then( function (response){
        if (response.status === 422) {
            return response;
        }
        
        if (!response.statusText === "OK") {
            throw json({ message: 'Could not save event.' }, { status: 500 });
        }

        console.log("response 전체 : ",response)
        let accessToken = response.headers.authorization; // 응답헤더에서 토큰 받기
        let refreshToken = response.headers.refresh; // 응답헤더에서 
        
        console.log('accessToken', accessToken);
        console.log('refreshToken', refreshToken);

        localStorage.setItem('token', response.accessToken);

        const expireation = new Date() // 만료시간 기능
        // const utcNow = expireation.getTime() + (expireation.getTimezoneOffset() * 60 * 1000); // 현재 시간을 utc로 변환한 밀리세컨드값
        // const koreaTimeDiff = 9 * 60 * 60 * 1000; // 한국 시간은 UTC보다 9시간 빠름(9시간의 밀리세컨드 표현)
        // const koreaNow = new Date(utcNow + koreaTimeDiff); // utc로 변환된 값을 한국 시간으로 변환시키기 위해 9시간(밀리세컨드)를 더함

        expireation.setHours(expireation.getHours() + 1);
    //    localStorage.setItem("expiration", expireation.toISOString());
        localStorage.setItem("expiration", new Date(expireation.getTime() - (expireation.getTimezoneOffset() * 60000)).toISOString());
        
        return response.data

    }).catch( function(error){
        console.log("error : ",error)
    })

    console.log("response : ",response)

    return redirect("/")


}