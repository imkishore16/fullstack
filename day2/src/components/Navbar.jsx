import { useState } from "react";
import { AiOutlineMenu, AiOutlineSearch, AiOutlineClose } from "react-icons/ai";
import { MdOutlineHorizontalRule } from "react-icons/md";
import { FaUserCircle } from "react-icons/fa";
import { TbTruckDelivery } from "react-icons/tb";
import { FaWallet } from "react-icons/fa";
import { MdFavorite, MdHelp } from "react-icons/md";
import { useNavigate } from "react-router-dom";
const Navbar = () => {
  const [nav, setNav] = useState(false);
  const navigate = useNavigate();

  const customerMenuItems = [
    { icon: <TbTruckDelivery size={25} className="mr-4"/>, text: "Plans" ,},
    { icon: <MdFavorite size={25} className="mr-4" />, text: "Addons" },
    { icon: <FaWallet size={25} className="mr-4" />, text: "Recharges" },
    { icon: <MdHelp size={25} className="mr-4" />, text: " FAQ" },
  ];
  const adminMenuItems = [
    { icon: <TbTruckDelivery size={25} className="mr-4" />, text: "Add Plans" },
    { icon: <MdFavorite size={25} className="mr-4" />, text: "Add Addons" },
    { icon: <FaWallet size={25} className="mr-4" onClick={()=>{navigate("/dashboard") }} />, text: "Dashboard" },
    // { icon: <MdHelp size={25} className="mr-4" />, text: "" },
  ];

  return (
    <div className="max-w-[1640px] mx-auto flex justify-between items-center p-4 shadow-sm">
      {/* Left side */}
      <div className="flex items-centerSET ">
        <div onClick={() => setNav(!nav)} className="cursor-pointer">
          <AiOutlineMenu size={30} />
        </div>
        <h1 className="text-2xl sm:text-3xl lg:text-4xl px-2">
          DO CO <span className="font-bold">MO</span>
        </h1>
        <div className="hidden lg:flex items-center bg-gray-200 rounded-full p-1 text-[14px] mx-4">
          <p className="bg-black text-white rounded-full p-2">Admin</p>
          <p className="p-2">Customer</p>
        </div>
      </div>

      {/* Search Input */}
      {/* <div className="bg-gray-200 rounded-full flex items-center px-2 w-[200px] sm:w-[400px] lg:w-[500px]">
        <AiOutlineSearch size={25} />
        <input
          className="bg-transparent p-2 w-full focus:outline-none"
          type="text"
          placeholder="Search Plans"
        />
      </div> */}
      <div className="hover:bg-white hover:text-black cursor-pointer rounded-[100px] p-2 " onClick={()=>navigate("/plans")} >
      <h1>View-Plans</h1>
      </div>
      <div className="hover:bg-white hover:text-black cursor-pointer rounded-[100px] p-2 " onClick={()=>navigate("/addons")}>
        <h1>View-Addons</h1>
      </div>
      <div className="hover:bg-white hover:text-black cursor-pointer rounded-[100px] p-2 " onClick={()=>navigate("/recharges")}>
        <h1 className="">Make-Payments</h1>
      </div>

      {/* <FaUserCircle size={50} className="mr-2 " /> */}

      <div className="relative inline-block group">
        <div className="flex items-center mr-16">
          <FaUserCircle
            size={50}
            className="mr-2 transition-opacity duration-300 opacity-100 group-hover:opacity-50"
          />
        </div>
        <div className="absolute hidden group-hover:block bg-white p-2 rounded-md shadow-lg mr-8">
          <button onClick={() => {}} className="text-gray-800 m-3">
            Profile
          </button>
          <button className="text-gray-800 m-3">Settings</button>
          <button className="text-gray-800 m-3">Logout</button>
        </div>
      </div>

      {nav ? (
        <div className="bg-black/80 fixed w-full h-screen z-10 top-0 left-0"></div>
      ) : (
        ""
      )}

      <div
        className={
          nav
            ? "fixed top-0 left-0 w-[300px] h-screen bg-white z-10 duration-300"
            : "fixed top-0 left-[-100%] w-[300px] h-screen bg-white z-10 duration-300"
        }
      >
        <AiOutlineClose
          onClick={() => setNav(!nav)}
          size={30}
          className="absolute right-4 top-4 cursor-pointer"
        />
        <h2 className="text-2xl p-4">
          DO CO <span className="font-bold">MO</span>
        </h2>
        <nav>
  <ul className="flex flex-col p-4 text-gray-800">
    {customerMenuItems.map(({ icon, text }, index) => {
      return (
        <div key={index} className="py-4">
          <li className="text-md flex cursor-pointer w-[100%] rounded-full mx-auto p-1 hover:text-white hover:bg-black">
            {icon} {text}
          </li>
        </div>
      );
    })}
  </ul>
  
  {sessionStorage.getItem('isLoggedIn') !== null && 
      <div className="my-5 flex items-center before:mt-0.5 before:flex-1 before:border-t before:border-neutral-300 after:mt-0.5 after:flex-1 after:border-t after:border-neutral-300">
      <p className="mx-4 mb-0 text-center font-semibold text-slate-500">
        Admin 
      </p>
    </div>
  }
  {sessionStorage.getItem('isLoggedIn') !== null && (
    
    <ul className="flex flex-col p-4 text-gray-800">
      {adminMenuItems.map(({ icon, text }, index) => {
        return (
          <div key={index} className="py-4">
            <li className="text-md flex cursor-pointer w-[100%] rounded-full mx-auto p-1 hover:text-white hover:bg-black">
              {icon} {text}
            </li>
          </div>
        );
      })}
    </ul>
  )}
</nav>

      </div>
    </div>
  );
};

export default Navbar;
