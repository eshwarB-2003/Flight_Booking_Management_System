import './common.css';

type Props = {
  label: string;
  onClick?: () => void;
  type?: "button" | "submit";
};

const Button = ({ label, onClick, type = "button" }: Props) => {
  return (
    <button className="btn" onClick={onClick} type={type}>
      {label}
    </button>
  );
};

export default Button;