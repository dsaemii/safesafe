import '../Style.css';
import {useState} from "react";

function LoginForm() {
    const [error, setError] = useState("");
    const [user, setUser] = useState("");
    const [password, setPassword] = useState("")

    function login() {
        if(user === "" || password === ""){
            setError("Please fill out the information")
        }
    }
    return (
        <div className="app center-screen">
            <section className='login'>
                <div className='head'>
                    <h1 className='name'>Safe Safe</h1>
                </div>
                <p className='msg'>Login</p>
                <div className='form'>
                    <input type="text" placeholder='Username' className='text' required value={user} onChange={(e) => setUser(e.target.value)}/><br/>
                    <input type="password" placeholder='Password' className='password' required value={password} onChange={(e) => setPassword(e.target.value)}/><br/>
                    <button className='btn-login' onClick={() => login()}>Login</button>
                    <div className={'error'}>{error}</div>
                </div>
                <br/>
            </section>
        </div>
    );
}

export default LoginForm;