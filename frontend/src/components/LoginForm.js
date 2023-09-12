import { Form } from "react-router-dom";
import { Link, useSubmit } from 'react-router-dom';

const LoginForm = () => {

    //const searchParams = new URL(request.url).searchParams;
    //const id = searchParams.get('id');
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");
    return(
    <>
    <Form method="POST">
        <label htmlFor="id" >아이디</label>
        <input type="text" name="id" id="id" defaultValue={id ? id : ""}></input>
        <label htmlFor="password">비밀번호</label>
        <input type="password" name="password" id="password"></input>
        <button>로그인</button>
    </Form>
    
    <div>
        <Link to="/signup">회원가입</Link>
    </div>


    </>
    )

}

export default LoginForm;