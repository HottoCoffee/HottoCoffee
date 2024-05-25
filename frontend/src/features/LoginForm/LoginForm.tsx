import { useForm } from "react-hook-form";
import { loginSchema, LoginSchema } from "./schema";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
  Form,
  FormControl,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { useSignIn } from "@/api/hooks/useSignIn";

export const LoginForm = () => {
  const signIn = useSignIn();

  const form = useForm<LoginSchema>({
    resolver: zodResolver(loginSchema),
    defaultValues: {},
  });

  return (
    <Form {...form}>
      <form
        className="flex gap-6 flex-col w-full f-full"
        onSubmit={form.handleSubmit((state) => {
          return signIn.mutateAsync({
            ...state,
          });
        })}
      >
        <FormField
          control={form.control}
          name="email"
          render={({ field }) => (
            <FormItem>
              <FormLabel>メールアドレス ※必須</FormLabel>
              <FormControl>
                <Input placeholder="example@example.com" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="password"
          render={({ field }) => (
            <FormItem>
              <FormLabel>パスワード ※必須</FormLabel>
              <FormControl>
                <Input type="password" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <Button type="submit">ログイン</Button>
      </form>
    </Form>
  );
};
