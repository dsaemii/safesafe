import '../Style.css';
import LoginForm from "./LoginForm";
import Dashboard from "./DashboardComponent";

function Login() {
    const isLoggedIn = false;
    return (
        <div>
            {!isLoggedIn && <LoginForm/>}
            {isLoggedIn && <Dashboard/>}
        </div>
    );
}

export default Login;
