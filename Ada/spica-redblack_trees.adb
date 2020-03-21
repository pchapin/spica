
with Ada.Unchecked_Deallocation;

package body Spica.RedBlack_Trees is

   procedure Deallocate_Node is new
     Ada.Unchecked_Deallocation(Object => Tree_Node, Name => Tree_Node_Access);

   procedure Left_Rotate(T : in out RedBlack_Tree; X : in Tree_Node_Access)
     with Pre => X.Right_Child /= T.Nil
   is
      Y : constant Tree_Node_Access := X.Right_Child;
   begin
      X.Right_Child := Y.Left_Child;
      if Y.Left_Child /= T.Nil then
         Y.Left_Child.Parent := X;
      end if;
      Y.Parent := X.Parent;
      if X.Parent = T.Nil then
         T.Root := Y;
      elsif X = X.Parent.Left_Child then
         X.Parent.Left_Child := Y;
      else
         X.Parent.Right_Child := Y;
      end if;
      Y.Left_Child := X;
      X.Parent := Y;
   end Left_Rotate;


   -- TODO: Finish Me!
   procedure Insert(T : in out RedBlack_Tree; Key : in Key_Type; Value : in Value_Type) is
   begin
      raise Program_Error;
   end Insert;


   function Search(T : in out RedBlack_Tree; Key : Key_Type) return Value_Type is
      Result : Value_Type;

      procedure Search_Subtree(Pointer : in Tree_Node_Access) is
      begin
         if Pointer = T.Nil then
            raise KV_Package.Not_Found;
         else
            if Key = Pointer.Key then
               Result := Pointer.Value;
            elsif Key < Pointer.Key then
               Search_Subtree(Pointer.Left_Child);
            else
               Search_Subtree(Pointer.Right_Child);
            end if;
         end if;
      end Search_Subtree;

   begin
      Search_Subtree(T.Root);
      return Result;
   end Search;


   -- TODO: Finish Me!
   procedure Delete(T : in out RedBlack_Tree; Key : in Key_Type) is
   begin
      raise Program_Error;
   end Delete;


   function Size(T : RedBlack_Tree) return Natural is
   begin
      return T.Count;
   end Size;


   procedure Check_Sanity(T : in RedBlack_Tree; Message : in String) is
   begin
      -- Okay! *cracks knuckles* Let's get to it...

      -- First, let's verify the ordering property. As a side effect this will also check the
      -- structuring of the left and right child pointers. If the children aren't connected
      -- properly, we will likely get Contraint_Error when we dereference null pointers, or
      -- maybe an infinite loop or some other detectable problem.
      declare
         procedure Check_Ordering(Pointer : in not null Tree_Node_Access) is
         begin
            if Pointer.Left_Child /= T.Nil then
               if not (Pointer.Left_Child.Key < Pointer.Key) then
                  raise KV_Package.Inconsistent_Store with Message;
               end if;
               Check_Ordering(Pointer.Left_Child);
            end if;
            if Pointer.Right_Child /= T.Nil then
               if not (Pointer.Key < Pointer.Right_Child.Key) then
                  raise KV_Package.Inconsistent_Store with Message;
               end if;
               Check_Ordering(Pointer.Right_Child);
            end if;
         end Check_Ordering;
      begin
         if T.Root /= T.Nil then
            Check_Ordering(T.Root);
         end if;
      end;

      -- Still here? Great!

      -- Now let's check those parent pointers...
      declare
         procedure Check_Parents(Pointer : in not null Tree_Node_Access) is
         begin
            if Pointer.Left_Child /= T.Nil then
               if Pointer.Left_Child.Parent /= Pointer then
                  raise KV_Package.Inconsistent_Store with Message;
               end if;
               Check_Parents(Pointer.Left_Child);
            end if;
            if Pointer.Right_Child /= T.Nil then
               if Pointer.Right_Child.Parent /= Pointer then
                  raise KV_Package.Inconsistent_Store with Message;
               end if;
               Check_Parents(Pointer.Right_Child);
            end if;
         end Check_Parents;
      begin
         if T.Root /= T.Nil then
            Check_Parents(T.Root);
            if T.Root.Parent /= T.Nil then
               raise KV_Package.Inconsistent_Store with Message;
            end if;
         end if;
      end;

      -- The crowd cheers as we turn the corner!

      -- Next, let's verify the node count.
      declare
         function Node_Count(Pointer : in not null Tree_Node_Access) return Natural is
            Left_Count, Right_Count : Natural := 0;
         begin
            if Pointer.Left_Child /= T.Nil then
               Left_Count := Node_Count(Pointer.Left_Child);
            end if;
            if Pointer.Right_Child /= T.Nil then
               Right_Count := Node_Count(Pointer.Right_Child);
            end if;
            return 1 + Left_Count + Right_Count;
         end Node_Count;
      begin
         if T.Root /= T.Nil then
            if T.Count /= Node_Count(T.Root) then
               raise KV_Package.Inconsistent_Store with Message;
            end if;
         else
            if T.Count /= 0 then
               raise KV_Package.Inconsistent_Store with Message;
            end if;
         end if;
      end;

      -- The home stretch! The crowd goes wild!!

      -- Finally, we have to check the red/black properties.
      declare
         -- "Property 4" is as listed in CLRS Chapter 13, page 308:
         -- If a node is red, then both its children are black.
         procedure Check_Property_4(Pointer : in not null Tree_Node_Access) is
         begin
            if Pointer = T.Nil then return; end if;

            if Pointer.Color = Red then
               if Pointer.Left_Child.Color /= Black or Pointer.Right_Child.Color /= Black then
                  raise KV_Package.Inconsistent_Store with Message;
               end if;
            end if;
            Check_Property_4(Pointer.Left_Child);
            Check_Property_4(Pointer.Right_Child);
         end Check_Property_4;

         Overall_Black_Height : Natural;
         pragma Unreferenced(Overall_Black_Height);  -- See note below.

         function Black_Height(Pointer : in not null Tree_Node_Access) return Natural is
            Left_Height, Right_Height : Natural := 0;
         begin
            -- Nil always has a black height of 0.
            if Pointer = T.Nil then return 0; end if;

            Left_Height  := Black_Height(Pointer.Left_Child);
            Right_Height := Black_Height(Pointer.Right_Child);

            -- Compute my black height along both downward paths...
            -- A Nil child is always black, which is appropriate here.
            if Pointer.Left_Child.Color = Black then
               Left_Height := Left_Height + 1;
            end if;
            if Pointer.Right_Child.Color = Black then
               Right_Height := Right_Height + 1;
            end if;

            -- The black height of a node is consistent across both children.
            if Left_Height /= Right_Height then
               raise KV_Package.Inconsistent_Store with Message;
            end if;

            -- Return that consistent height
            return Left_Height;
         end Black_Height;
      begin
         -- Property 2: The root is black.
         if T.Root /= T.Nil then
            if T.Root.Color /= Black then
               raise KV_Package.Inconsistent_Store with Message;
            end if;
         end if;

         -- Property 3: Nil is black.
         if T.Nil.Color /= Black then
            raise KV_Package.Inconsistent_Store with Message;
         end if;

         Check_Property_4(T.Root);

         -- Property 5: For each node, all simple paths from the node to descendant leaves
         -- contain the same number of black nodes. If this call returns without exception,
         -- the property is satisfied. The returned value is ignored.
         Overall_Black_Height := Black_Height(T.Root);
      end;

      -- The finish line! *fist pumps* The crowd chants, "RED! BLACK! RED! BLACK!..."

   end Check_Sanity;


   procedure Initialize(T : in out RedBlack_Tree) is
   begin
      T.Nil   := new Tree_Node;
      T.Nil.Color := Black;
      T.Root  := T.Nil;
      T.Count := 0;
   end Initialize;


   procedure Finalize(T : in out RedBlack_Tree) is

      procedure Deallocate_Subtree(Pointer : in out Tree_Node_Access) is
      begin
         if Pointer /= T.Nil then
            Deallocate_Subtree(Pointer.Left_Child);
            Deallocate_Subtree(Pointer.Right_Child);
            Deallocate_Node(Pointer);
         end if;
      end Deallocate_Subtree;

   begin
      Deallocate_Subtree(T.Root);
      T.Count := 0;
      Deallocate_Node(T.Nil);
   end Finalize;


end Spica.RedBlack_Trees;
