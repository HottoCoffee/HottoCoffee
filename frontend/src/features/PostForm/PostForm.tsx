import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { postFormSchema } from "./schema";
import { Button } from "@/components/ui/button";
import {
  FormField,
  FormItem,
  FormLabel,
  FormControl,
  FormMessage,
  Form,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { GrindSize } from "@/api/interfaces";
import { Textarea } from "@/components/ui/textarea";

export const PostForm = () => {
  const form = useForm<z.infer<typeof postFormSchema>>({
    resolver: zodResolver(postFormSchema),
    defaultValues: {
      temperature: 90,
    },
  });

  function onSubmit(values: z.infer<typeof postFormSchema>) {
    // Do something with the form values.
    // ✅ This will be type-safe and validated.
    console.log(values);
  }

  return (
    <Form {...form}>
      <form
        onSubmit={form.handleSubmit(onSubmit)}
        className="space-y-2 p-4 dark"
      >
        <FormField
          control={form.control}
          name="location"
          render={({ field }) => (
            <FormItem>
              <FormLabel>コーヒーを飲んだ場所</FormLabel>
              <FormControl>
                <Input placeholder="例）〇〇カフェ" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="origin"
          render={({ field }) => (
            <FormItem>
              <FormLabel>豆の産地(必須)</FormLabel>
              <FormControl>
                <Input placeholder="例）ブラジル" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="wayToBrew"
          render={({ field }) => (
            <FormItem>
              <FormLabel>コーヒーの淹れ方</FormLabel>
              <FormControl>
                <Input placeholder="例）ラテ、ドリップなど" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="roastLevel"
          render={({ field }) => (
            <FormItem>
              <FormLabel>豆の産地</FormLabel>
              <FormControl>
                <Select
                  onValueChange={field.onChange}
                  defaultValue={field.value}
                >
                  <SelectTrigger className="w-[180px]">
                    <SelectValue placeholder="Theme" />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="light">ライト</SelectItem>
                    <SelectItem value="cinnamon">シナモン</SelectItem>
                    <SelectItem value="medium">ミディアム</SelectItem>
                    <SelectItem value="high">ハイ</SelectItem>
                    <SelectItem value="city">シティ</SelectItem>
                    <SelectItem value="full_city">フルシティ</SelectItem>
                    <SelectItem value="french">フレンチ</SelectItem>
                    <SelectItem value="italian">イタリアン</SelectItem>
                  </SelectContent>
                </Select>
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="temperature"
          render={({ field }) => (
            <FormItem>
              <FormLabel>お湯の温度</FormLabel>
              <FormControl>
                <Input type="number" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="gramsOfCoffee"
          render={({ field }) => (
            <FormItem>
              <FormLabel>コーヒー豆のグラム数</FormLabel>
              <FormControl>
                <Input type="number" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="gramsOfWater"
          render={({ field }) => (
            <FormItem>
              <FormLabel>お湯のグラム数</FormLabel>
              <FormControl>
                <Input type="number" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="roastLevel"
          render={({ field }) => (
            <FormItem>
              <FormLabel>コーヒー豆の挽き方</FormLabel>
              <FormControl>
                <Select
                  onValueChange={field.onChange}
                  defaultValue={field.value}
                >
                  <SelectTrigger className="w-[180px]">
                    <SelectValue placeholder="Theme" />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value={GrindSize.finest}>極細挽き</SelectItem>
                    <SelectItem value={GrindSize.fine}>細挽き</SelectItem>
                    <SelectItem value={GrindSize.mediumFine}>
                      中細挽き
                    </SelectItem>
                    <SelectItem value={GrindSize.medium}>中挽き</SelectItem>
                    <SelectItem value={GrindSize.coarse}>粗挽き</SelectItem>
                  </SelectContent>
                </Select>
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="gramsOfWater"
          render={({ field }) => (
            <FormItem>
              <FormLabel>お湯のグラム数</FormLabel>
              <FormControl>
                <Textarea
                  placeholder="飲んだ感想"
                  className="resize-none"
                  {...field}
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <Button type="submit">Submit</Button>
      </form>
    </Form>
  );
};
