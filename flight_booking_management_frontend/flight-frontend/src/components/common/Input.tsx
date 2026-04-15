import './common.css';

type Props = {
  label: string;
  type?: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void
};

const Input = ({ label, type = "text", value, onChange }: Props) => {
  return (
    <div className="input-group">
      <label>{label}</label>
      <input type={type} value={value} onChange={onChange} />
    </div>
  );
};

export default Input;