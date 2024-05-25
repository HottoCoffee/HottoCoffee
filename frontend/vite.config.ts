import { defineConfig } from "vite";
import react from "@vitejs/plugin-react-swc";
import path from "path";
import basicSsl from "@vitejs/plugin-basic-ssl";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react(), basicSsl()],
  server: {
    port: 3000,
    host: "hottocoffee.example.com",
  },
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src"),
    },
  },
});
