import { useState } from "react";
import Input from "../../components/common/Input";
import Button from "../../components/common/Button";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = () => {
    console.log(email, password);
  };

  return (
    <div>
      <h2>Login</h2>
      <Input label="Email" value={email} onChange={(e) => setEmail(e.target.value)} />
      <Input label="Password" type="password" value={password} onChange={(e) => setPassword(e.target.value)} />

      <Button label="Login" onClick={handleLogin} />
    </div>
  );
};

export default Login;