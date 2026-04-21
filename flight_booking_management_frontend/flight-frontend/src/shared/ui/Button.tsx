type Props = React.ButtonHTMLAttributes<HTMLButtonElement>;

export default function Button({ children, ...props }: Props) {
  return (
    <button
      {...props}
      className="w-full p-3 rounded-lg bg-blue-600 text-white hover:bg-blue-700 transition"
    >
      {children}
    </button>
    
  );
}