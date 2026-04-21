import Sidebar from "./Sidebar";

export default function MainLayout({ children }: any) {
  return (
    <div className="flex min-h-screen bg-gradient-to-br from-sky-100 via-blue-200 to-indigo-300 relative overflow-hidden">

      <div className="absolute top-[-100px] left-[20%] w-[500px] h-[500px] bg-white opacity-20 rounded-full blur-3xl pointer-events-none"></div>
      <div className="absolute inset-0 bg-[radial-gradient(circle_at_top,_rgba(255,255,255,0.4),transparent_60%)] pointer-events-none"></div>

      {/* Sidebar */}
      <Sidebar />

      {/* Main content */}
      <div className="flex-1 p-6 relative z-10">
        {children}
      </div>
    </div>
  );
}