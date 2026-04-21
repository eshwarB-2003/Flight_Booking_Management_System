type Props = React.InputHTMLAttributes<HTMLInputElement>;

export default function Input(props: Props) {
  return (
    <input
      {...props}
      className="w-full p-3 rounded-lg bg-gray-100 focus:outline-none focus:ring-2 focus:ring-blue-500"
    />
  );
}