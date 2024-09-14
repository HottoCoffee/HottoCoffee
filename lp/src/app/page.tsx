import LP from "@/components/lp";
import type { Metadata } from "next/types";

export const metadata: Metadata = {
  title: "HottoCoffee",
  keywords: "coffee,log,blog,note",
};

export default function Home() {
  return (
    <main>
      <LP />
    </main>
  );
}
